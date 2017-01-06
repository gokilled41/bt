@echo off

if "%1" == "" goto a1
if not "%1" == "" goto b

: a1
echo please give one file name.
goto end

: b
echo format json: %~1
call acu formatjson "%~1"
goto end

: end
