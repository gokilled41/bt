@echo off

echo ****** FileUtil.addTypeAndRunItem() ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

call avtcp

set JAVA_OPTS=
rem set JAVA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y

java %JAVA_OPTS% -cp "%VT_CP%" gzhou.FileUtil addTypeAndRunItem %*
