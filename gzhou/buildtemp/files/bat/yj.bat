@echo off

set JEDI=C:\Users\gzhou\Desktop\rename
rem set JEDI=D:\jedi

if exist "%JEDI%\yoda_sjb" set YODA_TYPE=main
if exist "%JEDI%\yoda_main" set YODA_TYPE=sjb

if "%YODA_TYPE%"=="main" call goto a
if "%YODA_TYPE%"=="sjb" call goto b

:a
echo switch yoda to jboss upgrade
call all rn yoda\_ ##{n}_main sl
call all rn yoda/sjb ##{n4} sl
call goto end

:b
echo yoda is already jboss upgrade

:end
