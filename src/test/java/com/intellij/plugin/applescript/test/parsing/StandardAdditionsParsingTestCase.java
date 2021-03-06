package com.intellij.plugin.applescript.test.parsing;

import java.io.IOException;

public class StandardAdditionsParsingTestCase extends AbstractParsingFixtureTestCase {

  @Override
  protected String getMyTargetDirectoryPath() {
    return "standard_additions";
  }

  public void testScriptingAdditions() throws IOException {
    doParseScriptInPackageTest("scripting_additions");
  }

  public void testStandardAdditionsPackage() throws IOException {
    doParseAllInPackageTest();
  }

}
