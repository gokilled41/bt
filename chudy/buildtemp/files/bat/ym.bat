@echo off

set JEDI=D:\jedi
set SWITCH_YODA=true

if exist "%JEDI%\yoda_sjb" set YODA_TYPE=main
if exist "%JEDI%\yoda_main" set YODA_TYPE=sjb

if "%YODA_TYPE%"=="main" call goto b
if "%YODA_TYPE%"=="sjb" call goto a

:a
echo switch yoda to main
call all jedi yoda\_ ##{n}_sjb sl
call all jedi yoda/main ##{n4} sl
call goto end

:b
echo yoda is already main

:end

set SWITCH_YODA=
