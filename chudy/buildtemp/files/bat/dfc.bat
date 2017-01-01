@echo off

echo ****** DiffFileCopier.run() ******

set JAVA_HOME="C:\Program Files\Java\jdk1.8.0_51"

set VT_CP=C:\workspace\buildtemp\bin

call %JAVA_HOME%\bin\java -cp "%VT_CP%" gzhou.DiffFileCopier
