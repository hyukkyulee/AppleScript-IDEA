package com.intellij.plugin.applescript.lang.resolve;

import com.intellij.plugin.applescript.psi.AppleScriptComponent;
import com.intellij.plugin.applescript.psi.AppleScriptReferenceElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.intellij.plugin.applescript.psi.AppleScriptTokenTypesSets.HANDLER_DEFINITIONS;

/**
 * Andrey 20.04.2015
 */
public class AppleScriptComponentScopeResolver implements ResolveCache.AbstractResolver<AppleScriptReferenceElement,
        List<? extends PsiElement>> {

  public static final AppleScriptComponentScopeResolver INSTANCE = new AppleScriptComponentScopeResolver();

  @Override
  public List<? extends PsiElement> resolve(@NotNull AppleScriptReferenceElement scopeElement, boolean incompleteCode) {
    final Set<AppleScriptComponent> resultSet = new HashSet<>();

    // local
    PsiElement maxScope = getMaxScope(scopeElement);
    final AppleScriptComponentScopeProcessor resolveProcessor = new AppleScriptComponentScopeProcessor(resultSet);
    PsiTreeUtil.treeWalkUp(resolveProcessor, scopeElement, maxScope, ResolveState.initial());
    return new ArrayList<>(resultSet);
  }

  /**
   * @param scopeElement element for which max scope is calculated
   * @return max resolve scope for scopeElement
   */
  @Nullable
  private PsiElement getMaxScope(AppleScriptReferenceElement scopeElement) {
    return getHandlerDefinitionScope(scopeElement.getContext());//currently only handler def. are processed
  }

  @Nullable
  private PsiElement getHandlerDefinitionScope(PsiElement scopeElement) {
    PsiElement currentScope = scopeElement;
    while (currentScope != null) {
      IElementType elementType = currentScope.getNode().getElementType();
      if (HANDLER_DEFINITIONS.contains(elementType)) {
        return currentScope;
      }
      currentScope = currentScope.getContext();
    }
    return null;
  }

  private boolean isInsideHandlerDefinition(PsiElement context) {
    while (context != null) {
      IElementType elementType = context.getNode().getElementType();
      if (HANDLER_DEFINITIONS.contains(elementType)) {
        return true;
      }
      context = context.getContext();
    }
    return false;
  }
}
