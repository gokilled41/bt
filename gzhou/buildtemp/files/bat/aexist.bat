@echo off

if "%~1" == "" goto a1
if not "%~1" == "" goto b

: a1
echo please give one file name.
goto end

: b
call acu exist %*
goto end

: end
