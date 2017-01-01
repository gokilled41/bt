@echo off

rem It is one command to generate dataflow js files according to component xml
rem Give the component xml absolute file location as the parameter

echo ****** FileUtil.generateJSFileForDataflowComponent() ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51
set YODA_HOME=D:\jedi\yoda

set VT_CP=C:\workspace\buildtemp\bin
set VT_CP=%VT_CP%;%YODA_HOME%\export\home\jboss\server\vtba\lib\vtclient.jar
set VT_CP=%VT_CP%;%YODA_HOME%\export\home\jboss\server\vtba\lib\vtfc.jar
set VT_CP=%VT_CP%;%YODA_HOME%\export\home\jboss\server\vtba\lib\commons-logging.jar
set VT_CP=%VT_CP%;%YODA_HOME%\export\home\jboss\lib\endorsed\xercesImpl.jar
set VT_CP=%VT_CP%;%YODA_HOME%\export\projects\unbundled\dataflow\tools\dmsdk\sdk\dist\lib\vtdf-sdkframework.jar

java -cp "%VT_CP%" gzhou.FileUtil generateJSFileForDataflowComponent %1
