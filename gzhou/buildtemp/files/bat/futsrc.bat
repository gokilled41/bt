@echo off

echo ****** Translate source to target ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

call avtcp

java -cp "%VT_CP%" gzhou.FileUtil translate "C:\\\\Documents and Settings\\\\gzhou\\\\Desktop\\\\translate.txt" source target false
