@echo off

rem echo ****** FileUtil.updateSastFile() ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

call avtcp

java -cp "%VT_CP%" gzhou.FileUtil updateSastFile %1 %2
