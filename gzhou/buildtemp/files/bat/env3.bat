@echo off
rem -------------------------------------------------------------------------
rem Set environment variables
rem -------------------------------------------------------------------------

call satype
set VTBA_HOME=D:\gzhou\sa\OI_3
set JBOSS_HOME=%VTBA_HOME%\%JBOSS_DIR_NAME%
set ESMS=%VTBA_HOME%\esms
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51
set PATH=%JAVA_HOME%\bin;%R_HOME%\bin\x64;%SystemRoot%\system32;%SystemRoot%;%SystemRoot%\System32\Wbem;%ANT_HOME%\bin;%VTBA_HOME%\esms\bin;%VTBA_HOME%\bin;%BAT_HOME%
set HADOOP_HOME=D:\jedi\src\spark\hadoop-2.2.0
set SPARK_HOME=D:\soft\spark\spark-2.0.1-bin-hadoop2.4
