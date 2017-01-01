@echo off
if "%~1" == "" goto a1
if "%~2" == "" goto a1
if "%~3" == "" goto a1
if not "%~1" == "" goto b

: a1
call ap batdoc arename one nl l1
goto end

: b
call pa rename %*
goto end

: end
