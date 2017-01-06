@echo off

echo ****** Translate ASBTest to SourceTest ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

call avtcp

java -cp "%VT_CP%" gzhou.FileUtil translate D:\\\\jedi\\\\yoda\\\\unbundled\\\\apps\\\\hadoop_loader\\\\server\\\\libs\\\\devtests\\\\hdfssource\\\\build.xml ASBTest SourceTest true
