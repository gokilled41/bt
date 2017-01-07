@echo off

echo ****** DiffFileCopier.run() ******

set JAVA_HOME="C:\Program Files\Java\jdk1.8.0_51"

call avtcp

call %JAVA_HOME%\bin\java -cp "%VT_CP%" gzhou.DiffFileCopier
