@echo off

if "%1" == "" goto a
if not "%1" == "" goto b

: a
echo please give one table name.
goto end

: b
call sql mysql "select * from %1"
goto end

: end
