@echo off

if "%1" == "" goto a
if not "%1" == "" goto b

: a
echo please give one bat name.
goto end

: b
rem echo %1.bat: 
rem echo.
echo edit C:\Users\Chudy\Desktop\buildtemp\files\bat\%1.bat
call e C:\Users\Chudy\Desktop\buildtemp\files\bat\%1.bat
goto end

: end
