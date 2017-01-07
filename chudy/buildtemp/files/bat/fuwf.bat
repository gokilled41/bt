@echo off

echo ****** FileUtil.watchFile() ******
echo Watching: %1

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

call avtcp

java -cp "%VT_CP%" gzhou.FileUtil watchFile %1
