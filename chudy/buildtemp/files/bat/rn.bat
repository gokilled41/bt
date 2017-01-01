@echo off

if "%1" == "" goto a1
if "%2" == "" goto a2
if not "%1" == "" goto b

: a1
echo please give one bat name from.
goto end

: a2
echo please give one bat name to.
goto end

: b
echo rename %~1.bat -> %~2.bat
call al bat %~1.bat* acp bat mv ##%~2.bat sl
goto end

: end
