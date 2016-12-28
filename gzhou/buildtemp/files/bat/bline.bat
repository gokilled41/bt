@echo off
rem -------------------------------------------------------------------------
rem Set environment variables
rem -------------------------------------------------------------------------

set TMP=D:\soft\spark\tmp
set VTBA_HOME=D:\gzhou\sa\OI_1
set JBOSS_HOME=%VTBA_HOME%\jboss
set ESMS=%VTBA_HOME%\esms
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51
set PATH=%JAVA_HOME%\bin;%R_HOME%\bin\x64;%SystemRoot%\system32;%SystemRoot%;%SystemRoot%\System32\Wbem;%ANT_HOME%\bin;%VTBA_HOME%\esms\bin;%VTBA_HOME%\bin;%BAT_HOME%
set HADOOP_HOME=D:\soft\hadoop\hadoop-2.4.1
set SPARK_HOME=D:\soft\spark\spark-1.5.2-bin-hadoop2.4

rem -------------------------------------------------------------------------
rem Go to directory of Spark bin
rem -------------------------------------------------------------------------

d:
cd %SPARK_HOME%\bin

rem -------------------------------------------------------------------------
rem Start the Spark Shell
rem -------------------------------------------------------------------------

call beeline.cmd
