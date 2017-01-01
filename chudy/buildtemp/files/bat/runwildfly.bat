@echo off
rem -------------------------------------------------------------------------
rem Set environment variables
rem -------------------------------------------------------------------------

set VTBA_HOME=D:\jedi\yoda\export\home
set JBOSS_HOME=D:\soft\jboss\wildfly-10.0.0.CR4
set ESMS=%VTBA_HOME%\esms
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51
set PATH=%JAVA_HOME%\bin;%R_HOME%\bin\x64;%SystemRoot%\system32;%SystemRoot%;%SystemRoot%\System32\Wbem;%ANT_HOME%\bin;%VTBA_HOME%\esms\bin;%VTBA_HOME%\bin;%BAT_HOME%
set HADOOP_HOME=D:\jedi\src\spark\hadoop-2.2.0

rem -------------------------------------------------------------------------
rem Go to directory of JBoss bin
rem -------------------------------------------------------------------------

d:
cd %JBOSS_HOME%\bin

rem -------------------------------------------------------------------------
rem Start the JBoss
rem -------------------------------------------------------------------------

call standalone.bat --server-config=standalone-full.xml
