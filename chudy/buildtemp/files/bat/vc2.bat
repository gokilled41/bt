@echo off

if "%1" == "" goto a
if not "%1" == "" goto b

: a
echo please give one bat name.
goto end

: b
rem echo %1.bat: 
rem echo.

echo copy C:\workspace\buildtemp\files\bat\%1.bat C:\Users\Chudy\Desktop\rename\%1.bat
call copy C:\workspace\buildtemp\files\bat\%1.bat C:\Users\Chudy\Desktop\rename\%1.bat

call kbat

echo copy C:\Users\Chudy\Desktop\rename\%1.bat C:\Users\Chudy\Desktop\buildtemp\files\bat\%1.bat
call copy C:\Users\Chudy\Desktop\rename\%1.bat C:\Users\Chudy\Desktop\buildtemp\files\bat\%1.bat

goto end

: end
