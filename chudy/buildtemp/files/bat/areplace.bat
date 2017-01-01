@echo off
if "%~1" == "" goto a1
if "%~2" == "" goto a1
if not "%~1" == "" goto b

: a1
call ap batdoc areplace one nl l1
goto end

: b
call pa replace %*
goto end

: end
