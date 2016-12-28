@echo off
if "%~1" == "" goto a1
if not "%~1" == "" goto b

: a1
call ap batdoc aprint one nl l1
goto end

: b
call pa print %*
goto end

: end
