package com.intellij.plugin.applescript;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.plugin.applescript.psi.AppleScriptPsiElement;
import com.intellij.plugin.applescript.psi.impl.AppleScriptElementPresentation;
import com.intellij.plugin.applescript.psi.impl.AppleScriptPsiElementImpl;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Andrey 22.02.2015
 */
public class AppleScriptFile extends PsiFileBase implements AppleScriptPsiElement {
  public AppleScriptFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, com.intellij.plugin.applescript.AppleScriptLanguage.INSTANCE);
  }

  @Override
  public boolean processDeclarations(@NotNull PsiScopeProcessor processor, 
                                     @NotNull ResolveState state, 
                                     PsiElement lastParent, 
                                     @NotNull PsiElement place) {
    return AppleScriptPsiElementImpl.processDeclarationsImpl(this, processor, state, lastParent, place) 
        && super.processDeclarations(processor, state, lastParent, place);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return com.intellij.plugin.applescript.AppleScriptFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return "AppleScript File";
  }

  @Override
  public Icon getIcon(int flags) {
    return AppleScriptIcons.FILE;
  }

  @Override
  public ItemPresentation getPresentation() {

    return new AppleScriptElementPresentation(this) {
      @Nullable
      @Override
      public String getPresentableText() {
        return super.getPresentableText();
      }
    };
  }
}