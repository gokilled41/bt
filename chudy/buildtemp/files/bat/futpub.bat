@echo off

echo ****** Translate publisher to subscriber ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

set VT_CP=C:\workspace\buildtemp\bin

java -cp "%VT_CP%" gzhou.FileUtil translate "C:\\\\Documents and Settings\\\\gzhou\\\\Desktop\\\\translate.txt" publisher subscriber false
