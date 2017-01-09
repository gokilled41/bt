@echo off

set JEDI=D:\jedi
set SWITCH_YODA=true

if exist "%JEDI%\yoda_sjb" set YODA_TYPE=main
if exist "%JEDI%\yoda_main" set YODA_TYPE=sjb

if "%YODA_TYPE%"=="main" call goto a
if "%YODA_TYPE%"=="sjb" call goto b

:a
echo switch yoda to jboss upgrade
call all jedi yoda\_ ##{n}_main sl
call all jedi yoda/sjb ##{n4} sl
call goto end

:b
echo yoda is already jboss upgrade

:end

set SWITCH_YODA=
