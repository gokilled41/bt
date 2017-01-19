@echo off

if "%~1" == "o" goto a1
if "%~1" == "" goto b

: a1
call netstat -nao > D:\alogs\ns.log
call uedit D:\alogs\ns.log
goto end

: b
call netstat -nao
goto end

: end
