@echo off

echo ****** Translate JSONTest to SourceTest ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

set VT_CP=C:\workspace\buildtemp\bin

java -cp "%VT_CP%" gzhou.FileUtil translate D:\\\\jedi\\\\yoda\\\\unbundled\\\\apps\\\\hadoop_loader\\\\server\\\\libs\\\\devtests\\\\hdfssource\\\\build.xml JSONTest SourceTest true
