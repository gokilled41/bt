@echo off
if "%~1" == "" goto a1
if "%~2" == "" goto a1
if not "%~1" == "" goto b

: a1
call ap batdoc acopy one nl l1
goto end

: b
call pa copy %*
goto end

: end
