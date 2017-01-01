@echo off
if "%~1" == "" goto a1
if not "%~1" == "" goto b

: a1
call ap batdoc alist one nl l1
goto end

: b
call pa list %*
goto end

: end
