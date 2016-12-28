@echo off

echo ****** FileUtil.generateAllBatFilesNoCommonOnlyFile() ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

set VT_CP=C:\workspace\buildtemp\bin

java -cp "%VT_CP%" gzhou.FileUtil generateAllBatFilesNoCommonOnlyFile
