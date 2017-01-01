@echo off

if "%1" == "" goto a1
if "%2" == "" goto a2
if not "%1" == "" goto b

: a1
echo please give one bat name copy from.
goto end

: a2
echo please give one bat name copy to.
goto end

: b
call copy C:\workspace\buildtemp\files\bat\%1.bat C:\workspace\buildtemp\files\bat\%2.bat
goto end

: end

