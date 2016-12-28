@echo off
rem -------------------------------------------------------------------------
rem Set environment variables
rem -------------------------------------------------------------------------

set VTBA_HOME=D:\jedi\yoda\export\home
set JBOSS_HOME=%VTBA_HOME%\jboss
set ESMS=%VTBA_HOME%\esms
set JAVA_HOME=%JAVA_HOME_JDK8%
set PATH=%JAVA_HOME%\bin;%R_HOME%\bin\x64;%SystemRoot%\system32;%SystemRoot%;%SystemRoot%\System32\Wbem;%ANT_HOME%\bin;%VTBA_HOME%\esms\bin;%VTBA_HOME%\bin

rem -------------------------------------------------------------------------
rem Go to directory of JBoss bin
rem -------------------------------------------------------------------------

d:
cd %JBOSS_HOME%\bin

rem -------------------------------------------------------------------------
rem Start the JBoss
rem -------------------------------------------------------------------------

call run -c vtba -b 0.0.0.0
