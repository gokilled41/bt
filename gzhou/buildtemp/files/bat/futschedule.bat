@echo off

echo ****** Translate schedule ******

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51

call avtcp

java -cp "%VT_CP%" gzhou.FileUtil translate "C:\\\\workspace\\\\buildtemp\\\\files\\\\bat\\\\schsr.bat" DelphiServerSchedule ServerSchedule_Post41 true
java -cp "%VT_CP%" gzhou.FileUtil translate "C:\\\\workspace\\\\buildtemp\\\\files\\\\bat\\\\schst.bat" DelphiServerSchedule ServerSchedule_Post41 true
java -cp "%VT_CP%" gzhou.FileUtil translate "C:\\\\workspace\\\\buildtemp\\\\files\\\\bat\\\\schsup.bat" DelphiServerSchedule ServerSchedule_Post41 true
