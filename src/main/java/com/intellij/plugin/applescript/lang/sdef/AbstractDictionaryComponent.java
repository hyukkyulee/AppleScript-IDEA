package com.intellij.plugin.applescript.lang.sdef;

import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.plugin.applescript.lang.AppleScriptComponentType;
import com.intellij.plugin.applescript.lang.ide.AppleScriptDocHelper;
import com.intellij.plugin.applescript.psi.AppleScriptExpression;
import com.intellij.plugin.applescript.psi.impl.AppleScriptElementPresentation;
import com.intellij.plugin.applescript.psi.sdef.DictionaryIdentifier;
import com.intellij.plugin.applescript.psi.sdef.impl.DictionaryComponentBase;
import com.intellij.plugin.applescript.psi.sdef.impl.DictionaryIdentifierImpl;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * Andrey 09.07.2015
 */
public abstract class AbstractDictionaryComponent<P extends DictionaryComponent>
    extends DictionaryComponentBase<P, XmlTag> implements DictionaryComponent {

  @NotNull
  private final String code;
  @NotNull
  private final String name;
  @Nullable
  private String description;
  @Nullable
  protected String dictionaryDoc;

  protected AbstractDictionaryComponent(@NotNull P parent, @NotNull String name, @NotNull String code,
                                        @NotNull XmlTag myXmlTag, @Nullable String description) {
    super(myXmlTag, parent);
    this.code = code;
    this.name = name;
    this.description = description;
  }

  protected AbstractDictionaryComponent(@NotNull P parent, @NotNull String name,
                                        @NotNull String code, @NotNull XmlTag myXmlTag) {
    super(myXmlTag, parent);
    this.code = code;
    this.name = name;
  }

  @Override
  public boolean isScriptProperty() {
    return false;
  }

  @Override
  public boolean isHandler() {
    return false;
  }

  @Nullable
  @Override
  public PsiElement getOriginalDeclaration() {
    return this;
  }

  @Override
  public boolean isObjectProperty() {
    return false;
  }

  @Override
  public boolean isVariable() {
    return false;
  }

  @Nullable
  @Override
  public AppleScriptExpression findAssignedValue() {
    return null;
  }

  @NotNull
  @Override
  public String getName() {
    return name;
  }

  @NotNull
  @Override
  public List<String> getNameIdentifiers() {
    return Arrays.asList(name.split("\\s+"));
  }

  @NotNull
  @Override
  public String getQualifiedName() {
    return getDictionaryParentComponent().getQualifiedName() + "/" + getShortQname();
  }

  @NotNull
  private String getShortQname() {
    return getType().substring(11) + ":" + getCode();
  }

  @NotNull
  @Override
  public String getQualifiedPath() {
    return getDictionaryParentComponent().getQualifiedPath() + "/" + getShortQname();
  }

  @NotNull
  @Override
  public String getType() {
    //prefixed with "dictionary " to distinguish from native AppleScript types..
    AppleScriptComponentType componentType = AppleScriptComponentType.typeOf(this);
    return componentType != null ? componentType.toString().toLowerCase() : "dictionary reference";
  }

  @Override
  public void setDescription(@Nullable String description) {
    this.description = description;
  }

  @NotNull
  @Override
  public ApplicationDictionary getDictionary() {
    return getSuite().getDictionary();
  }

  @Nullable
  @Override
  public String getCocoaClassName() {
    return null;
  }

  protected String getDocHeader() {
    StringBuilder sb = new StringBuilder();
    String name = getName();
    sb.append("<b>");
    AppleScriptDocHelper.appendElementLink(sb, getDictionary(), getDictionary().getName());
    sb.append("</b> ").append(" Dictionary").append("<br>");
    return sb.toString();
  }

  protected String getTypeDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append("<p>");
    String type = StringUtil.capitalizeWords(getType(), true);
    sb.append(type.toLowerCase().contains("dictionary") ? type.substring(10) : type).append(" <b>").append(name).append("</b>");
    if (this instanceof AppleScriptClass) {
      AppleScriptClass parentClass = ((AppleScriptClass) this).getParentClass();
      if (parentClass != null) {
        sb.append(" [inh. ");
        String ext = "";
        int recursionGuard = 15;
        do {
          recursionGuard--;
          sb.append(ext);
          AppleScriptDocHelper.appendElementLink(sb, parentClass, parentClass.getName());
          parentClass = parentClass.getParentClass();
          ext = " > ";
        } while (parentClass != null && recursionGuard > 0);
        sb.append(" ]");
      }
    } else if (this instanceof CommandParameter) {
      CommandParameter param = (CommandParameter) this;
      String pType = StringUtil.notNullize(param.getTypeSpecifier());
      sb.append(" [").append(pType).append("]");
    }
    sb.append(" : ").append(StringUtil.notNullize(getDescription()));
    return sb.toString();
  }

  @NotNull
  @Override
  public String getDocumentation() {
    return getDocHeader() + getTypeDescription() + getDocFooter();
  }

  protected String getDocFooter() {
    return "</p>";
  }

  @NotNull
  @Override
  public String getCode() {
    return code;
  }

  @Nullable
  public String getDescription() {
    return description;
  }

  @NotNull
  @Override
  public abstract Suite getSuite();

  @Nullable
  @Override
  public Icon getIcon(boolean open) {
    AppleScriptComponentType componentType = AppleScriptComponentType.typeOf(this);
    return componentType != null ? componentType.getIcon() : null;
  }

  @Override
  public ItemPresentation getPresentation() {
    return new AppleScriptElementPresentation(this);
  }

  @Nullable
  @Override
  public String getLocationString() {
    return getQualifiedPath();
  }

  @NotNull
  @Override
  public DictionaryIdentifier getIdentifier() {
    DictionaryIdentifier myIdentifier = null;
    XmlAttribute nameAttr = myXmlElement.getAttribute("name");
    if (nameAttr != null) {
      XmlAttributeValue attrValue = nameAttr.getValueElement();
      if (attrValue != null) {
        myIdentifier = new DictionaryIdentifierImpl(this, getName(), attrValue);
      }
    } else {
      for (XmlAttribute anyAttr : myXmlElement.getAttributes()) {
        myIdentifier = new DictionaryIdentifierImpl(this, getName(), anyAttr);
      }
    }
    return myIdentifier != null ? myIdentifier : new DictionaryIdentifierImpl(this, getName(), myXmlElement.getAttributes()[0]);
  }

  @Nullable
  @Override
  public PsiElement getNameIdentifier() {
    return getIdentifier();
  }

  public void setDictionaryDoc(@Nullable String documentation) {
    this.dictionaryDoc = documentation;
  }
}
