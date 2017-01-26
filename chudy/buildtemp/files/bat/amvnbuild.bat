@echo off

if "%~1" == "" goto a1
if not "%~1" == "" goto b

: a1
echo please give one type and run alias.
goto end

: b
call go %1
call sj7
call mp
call sj8
call go bt
goto end

: end