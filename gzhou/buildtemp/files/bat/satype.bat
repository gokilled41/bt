@echo off
call aexist j1
if "%AEXIST%"=="true" goto OI7
if "%AEXIST%"=="" goto OI6

: OI6
set SA_TYPE=OI6
set JBOSS_DIR_NAME=jboss
goto end

: OI7
set SA_TYPE=OI7
set JBOSS_DIR_NAME=wildfly
goto end

: end