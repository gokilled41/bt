@echo off

rem echo ****** FileUtil.sql() ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

call avtcp

java -cp "%VT_CP%" gzhou.FileUtil sql %*
