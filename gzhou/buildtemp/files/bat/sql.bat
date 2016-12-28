@echo off

rem echo ****** FileUtil.sql() ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

set VT_CP=C:\workspace\buildtemp\bin;C:\workspace\buildtemp\lib\mysql-connector-java-5.1.17-bin.jar

java -cp "%VT_CP%" gzhou.FileUtil sql %*