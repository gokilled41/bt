@echo off

rem echo ****** FileUtil.git() ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

call avtcp

set JAVA_OPTS=-Xms16m -Xmx1024m
if "%PADebugPort%"=="" set PADebugPort=8787
if "%ADEBUG%"=="true" set JAVA_OPTS=%JAVA_OPTS% -Xdebug -Xrunjdwp:transport=dt_socket,address=%PADebugPort%,server=y,suspend=y

java %JAVA_OPTS% -cp "%VT_CP%" gzhou.FileUtil git %*
