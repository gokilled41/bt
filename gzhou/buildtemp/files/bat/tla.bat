@echo off

if "%~1" == "o" goto a1
if "%~1" == "" goto b

: a1
call btdir
call ant task-list-all > D:\alogs\tla.log
call uedit D:\alogs\tla.log
goto end

: b
call btdir
call ant task-list-all
goto end

: end
