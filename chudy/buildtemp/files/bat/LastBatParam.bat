@echo off

set LastBatParam=%1

: a

if "%~1" == "" goto end
set LastBatParam=%~1
if "%~1" == "-d" goto end
shift

goto a


: end
rem echo last param: %LastBatParam%
