rem -------------------------------------------------------------------------
rem Set environment variables
rem -------------------------------------------------------------------------

call ye

if "%YODA_TYPE%"=="main" goto main
if "%YODA_TYPE%"=="sjb" goto sjb

: main
set JBOSS_DIR_NAME=jboss
goto go

: sjb
set JBOSS_DIR_NAME=wildfly
goto go

: go
set VTBA_HOME=D:\jedi\yoda\export\home
set JBOSS_HOME=%VTBA_HOME%\%JBOSS_DIR_NAME%
set ESMS=%VTBA_HOME%\esms
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51
set PATH=%JAVA_HOME%\bin;%R_HOME%\bin\x64;%SystemRoot%\system32;%SystemRoot%;%SystemRoot%\System32\Wbem;%ANT_HOME%\bin;%VTBA_HOME%\esms\bin;%VTBA_HOME%\bin;%BAT_HOME%
set HADOOP_HOME=D:\jedi\src\spark\hadoop-2.2.0
set SPARK_HOME=D:\soft\spark\spark-2.0.1-bin-hadoop2.4
rem set MVN_URL=http://maven.aliyun.com/nexus/content/groups/public/
