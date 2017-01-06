@echo off

echo ****** Translate Only EpochTest to All BC Unit Test ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

call avtcp

call java -cp "%VT_CP%" gzhou.FileUtil translate D:\\\\jedi\\\\yoda\\\\bw\\\\devtests\\\\bc\\\\junit\\\\build.xml "<!--<test name=\"com.vitria.workflow.test.bc.BusinessCalendarTest\"" "<test name=\"com.vitria.workflow.test.bc.BusinessCalendarTest\"" true
call java -cp "%VT_CP%" gzhou.FileUtil translate D:\\\\jedi\\\\yoda\\\\bw\\\\devtests\\\\bc\\\\junit\\\\build.xml "--><test name=\"com.vitria.workflow.test.bc.EpochTest\"" "<test name=\"com.vitria.workflow.test.bc.EpochTest\"" true
