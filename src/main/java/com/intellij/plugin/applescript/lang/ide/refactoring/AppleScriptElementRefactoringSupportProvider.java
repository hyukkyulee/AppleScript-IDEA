package com.intellij.plugin.applescript.lang.ide.refactoring;

import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.plugin.applescript.psi.AppleScriptHandler;
import com.intellij.plugin.applescript.psi.AppleScriptNamedElement;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * Andrey 03.05.2015
 */
public class AppleScriptElementRefactoringSupportProvider extends RefactoringSupportProvider {
  @Override
  public boolean isSafeDeleteAvailable(@NotNull PsiElement element) {
    return element instanceof AppleScriptNamedElement;
  }

  @Override
  public boolean isInplaceRenameAvailable(@NotNull PsiElement element, PsiElement context) {
    return !(element instanceof AppleScriptHandler);
  }
}
