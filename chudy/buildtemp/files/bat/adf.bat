@echo off

if "%~1" == "" goto a1
if not "%~1" == "" goto b

: a1
echo adf file1 file2
goto end

: b
call set DisablePADebug=disable
call acu diff %*
call set DisablePADebug=
goto end

: end
