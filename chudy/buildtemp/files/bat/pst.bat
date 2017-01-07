@echo off

echo ****** FileUtil.p2st() ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

call avtcp

java -cp "%VT_CP%" gzhou.FileUtil p2st
