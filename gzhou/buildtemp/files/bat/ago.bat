@echo off

if "%1" == "" goto a1
if not "%1" == "" goto b

: a1
echo please give one dir name.
goto end

: b
call all %* ago
goto end

: end
