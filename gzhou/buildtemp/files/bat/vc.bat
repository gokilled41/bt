@echo off

if "%~1" == "" goto a
if not "%~1" == "" goto b

: a
echo please give one bat name.
goto end

: b
rem echo %1.bat: 
rem echo.

echo copy C:\workspace\buildtemp\files\bat\%1.bat C:\Users\gzhou\Desktop\rename\%1.bat
call copy C:\workspace\buildtemp\files\bat\%1.bat C:\Users\gzhou\Desktop\rename\%1.bat

call kbat2

echo copy C:\Users\gzhou\Desktop\rename\%1.bat C:\Users\gzhou\Desktop\buildtemp\files\bat\%1.bat
call copy C:\Users\gzhou\Desktop\rename\%1.bat C:\Users\gzhou\Desktop\buildtemp\files\bat\%1.bat

goto end

: end