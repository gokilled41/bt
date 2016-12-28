@echo off

if "%1" == "" goto a1
if not "%1" == "" goto b

: a1
echo please give one alias name.
goto end

: b
call tajgen %*
call tajtmp
call del C:\workspace\buildtemp\files\bat\tajtmp.bat
goto end

: end

