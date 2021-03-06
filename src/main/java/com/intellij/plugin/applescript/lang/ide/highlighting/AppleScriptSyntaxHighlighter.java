/*
 * Copyright (C) 2014, Cast & Crew (R) Software, LLC
 * All rights reserved.  Unauthorized disclosure or distribution is prohibited.
 */
package com.intellij.plugin.applescript.lang.ide.highlighting;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.plugin.applescript.lang.lexer._AppleScriptLexer;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.intellij.plugin.applescript.psi.AppleScriptTokenTypesSets.*;
import static com.intellij.plugin.applescript.psi.AppleScriptTypes.*;

public class AppleScriptSyntaxHighlighter extends SyntaxHighlighterBase {

  private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<>();

  static {
    fillMap(ATTRIBUTES, OPERATORS, AppleScriptSyntaxHighlighterColors.OPERATION_SIGN);
    fillMap(ATTRIBUTES, KEYWORDS, AppleScriptSyntaxHighlighterColors.KEYWORD);
    fillMap(ATTRIBUTES, AppleScriptSyntaxHighlighterColors.DICTIONARY_COMMAND_ATTR, DICTIONARY_COMMAND_NAME);
    fillMap(ATTRIBUTES, AppleScriptSyntaxHighlighterColors.DICTIONARY_COMMAND_SELECTOR_ATTR, COMMAND_PARAMETER_SELECTOR);
    fillMap(ATTRIBUTES, AppleScriptSyntaxHighlighterColors.DICTIONARY_CLASS_ATTR, DICTIONARY_CLASS_NAME);
    fillMap(ATTRIBUTES, AppleScriptSyntaxHighlighterColors.DICTIONARY_PROPERTY_ATTR, DICTIONARY_PROPERTY_NAME);
    fillMap(ATTRIBUTES, STRINGS, AppleScriptSyntaxHighlighterColors.STRING);
    fillMap(ATTRIBUTES, NUMBERS, AppleScriptSyntaxHighlighterColors.NUMBER);
    ATTRIBUTES.put(COMMENT, AppleScriptSyntaxHighlighterColors.COMMENT);
  }


  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new FlexAdapter(new _AppleScriptLexer(null));
  }

  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    return pack(ATTRIBUTES.get(tokenType));
  }
}
