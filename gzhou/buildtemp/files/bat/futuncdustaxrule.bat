@echo off

echo ****** Translate StaxEvaluatorTest to RuleTest ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

call avtcp

java -cp "%VT_CP%" gzhou.FileUtil translate D:\\\\jedi\\\\yoda\\\\unbundled\\\\af\\\\java\\\\nc_framework\\\\devtests\\\\utility\\\\build.xml StaxEvaluatorTest RuleTest true
