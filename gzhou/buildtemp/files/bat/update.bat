@echo off

if "%1" == "" goto a1
if not "%1" == "" goto b

: a1
echo please give one type and run alias.
goto end

: b
call go %1
call sj7
echo updating %1
call sup
call sj8
goto end

: end