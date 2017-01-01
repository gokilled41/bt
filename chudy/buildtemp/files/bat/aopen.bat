@echo off
if "%~1" == "" goto a1
if not "%~1" == "" goto b

: a1
call ap batdoc aopen one nl l1
goto end

: b
call palias open %*
call aopentmp
call bdel aopentmp
goto end

: end
