@echo off

echo ****** FileUtil.listJavaClasses() ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

set VT_CP=C:\workspace\buildtemp\bin

set JAVA_OPTS=
rem set JAVA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y

java %JAVA_OPTS% -cp "%VT_CP%" gzhou.FileUtil listJavaClasses %*
