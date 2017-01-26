@echo off

if "%~1" == "" goto a1
if "%~2" == "" goto a2
if not "%~1" == "" goto b

: a1
echo please give one dir name.
goto end

: a2
echo please give one class name.
goto end

: b
echo find class "%~2" in folder "%~1".
call jlist %*
goto end

: end
