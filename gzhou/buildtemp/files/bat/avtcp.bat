@echo off

rem buildtemp bin
set VT_CP=C:\workspace\buildtemp\bin

rem buildtemp lib
set BT_LIB=C:\workspace\buildtemp\lib
call set VT_CP=%VT_CP%;%BT_LIB%\vttestutil.jar
call set VT_CP=%VT_CP%;%BT_LIB%\jackson-annotations-2.1.1.jar
call set VT_CP=%VT_CP%;%BT_LIB%\jackson-core-2.1.1.jar
call set VT_CP=%VT_CP%;%BT_LIB%\jackson-databind-2.1.1.jar
call set VT_CP=%VT_CP%;%BT_LIB%\commons-logging.jar
call set VT_CP=%VT_CP%;%BT_LIB%\dom4j-1.6.1.jar
call set VT_CP=%VT_CP%;%BT_LIB%\xercesImpl-2.8.1.jar
call set VT_CP=%VT_CP%;%BT_LIB%\commons-codec.jar
call set VT_CP=%VT_CP%;%BT_LIB%\mysql-connector-java-5.1.17-bin.jar
call set VT_CP=%VT_CP%;%BT_LIB%\ojdbc-11.1.0.6.jar

if "%SWITCH_YODA%"=="true" goto end

rem VTBA_HOME
set VTBA_HOME=D:\jedi\yoda\export\home
call set VT_CP=%VT_CP%;%VTBA_HOME%\jboss\server\vtba\lib\vtclient.jar
call set VT_CP=%VT_CP%;%VTBA_HOME%\jboss\server\vtba\lib\vtfc.jar
call set VT_CP=%VT_CP%;%VTBA_HOME%\jboss\server\vtba\lib\commons-logging.jar

: end
