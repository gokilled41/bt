@echo off

echo ****** Translate RuleTest to StaxEvaluatorTest ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

set VT_CP=C:\workspace\buildtemp\bin

java -cp "%VT_CP%" gzhou.FileUtil translate D:\\\\jedi\\\\yoda\\\\unbundled\\\\af\\\\java\\\\nc_framework\\\\devtests\\\\utility\\\\build.xml RuleTest StaxEvaluatorTest true
