@echo off

if "%~1" == "" goto a1
if not "%~1" == "" goto b

: a1
echo e file
goto end

: b
call acu edit %*
goto end

: end
