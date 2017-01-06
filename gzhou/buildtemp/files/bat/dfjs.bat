@echo off

rem It is one command to generate dataflow js files according to component xml
rem Give the component xml absolute file location as the parameter

echo ****** FileUtil.generateJSFileForDataflowComponent() ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51
set YODA_HOME=D:\jedi\yoda

call avtcp
call set VT_CP=%VT_CP%;%YODA_HOME%\export\projects\unbundled\dataflow\tools\dmsdk\sdk\dist\lib\vtdf-sdkframework.jar

java -cp "%VT_CP%" gzhou.FileUtil generateJSFileForDataflowComponent %1
