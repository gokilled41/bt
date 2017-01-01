@echo off

if "%1" == "" goto a
if not "%1" == "" goto b

: a
echo please give one bug number.
goto end

: b
echo moving VITR00%~1.txt into done.
call all dsb %~1 acp dsb/done ##1_VITR00%~1.txt sl
call all dsb %~1 arm sl
goto end

: end