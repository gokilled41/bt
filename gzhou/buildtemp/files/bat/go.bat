@echo off

if "%1" == "" goto a
if not "%1" == "" goto b

: a
echo please give one alias name in typeandrun.
goto end

: b
rem echo go to dir "%1"
call godir %1
call gothisdir
call del C:\workspace\buildtemp\files\bat\gothisdir.bat
goto end

: end