@echo off

set JEDI=C:\Users\Chudy\Desktop\rename
rem set JEDI=D:\jedi

if exist "%JEDI%\yoda_sjb" set YODA_TYPE=main
if exist "%JEDI%\yoda_main" set YODA_TYPE=sjb

if "%YODA_TYPE%"=="main" call goto b
if "%YODA_TYPE%"=="sjb" call goto a

:a
echo switch yoda to main
call all rn yoda\_ ##{n}_sjb sl
call all rn yoda/main ##{n4} sl
call goto end

:b
echo yoda is already main

:end