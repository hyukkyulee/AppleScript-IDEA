package com.idea.plugin.applescript.lang.sdef;

import com.idea.plugin.applescript.lang.sdef.impl.ApplicationDictionary;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Andrey on 09.07.2015.
 */
public interface DictionaryComponent extends PsiElement, PsiNamedElement, NavigationItem {

  @Nullable
  String getCode();

  @Nullable
  String getCocoaClassName();

  @Override
  @NotNull
  String getName();

  @NotNull
  String getQualifiedPath();

  /**
   * Name, starting with suite code name and including component code.
   * Note: not necessarily unique (could be 2 commands with the same code in the suite)
   *
   * @return name string
   */
  @NotNull
  String getQualifiedName();

  @Nullable
  String getDescription();

  @Nullable
  Suite getSuite();

  @Nullable
  DictionaryComponent getDictionaryParentComponent();

  @NotNull
  String getType();

  void setDescription(String description);

  @NotNull
  ApplicationDictionary getDictionary();

}
