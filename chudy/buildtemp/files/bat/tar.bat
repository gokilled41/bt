@echo off

if "%~1" == "" goto a1
if "%~2" == "" goto a2
if not "%~1" == "" goto b

: a1
echo please give one alias name from.
goto end

: a2
echo please give one alias name to.
goto end

: b
call targen rename %*
call tartmp
call del C:\workspace\buildtemp\files\bat\tartmp.bat
goto end

: end
