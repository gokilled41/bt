@echo off

if "%1" == "" goto a1
if not "%1" == "" goto b

: a1
echo please give one alias name.
goto end

: b
call tasgen %*
call tastmp
call del C:\workspace\buildtemp\files\bat\tastmp.bat
goto end

: end

