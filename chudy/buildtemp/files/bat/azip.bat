@echo off

if "%~1" == "" goto a1
if "%~2" == "" goto a2
if not "%~1" == "" goto b

: a1
echo please give one from dir.
goto end

: a2
echo please give one to dir.
goto end

: a3
echo please give one file name.
goto end

: b

set azipFromDir=%~1
set azipToDir=%~2
set azipFileName=%~3

echo zipping "%azipFromDir%" to "%azipToDir%\%azipFileName%"

call set dirCallZip=%cd%
call go "%azipFromDir%"
call jar cf "%azipFileName%" *
call acp "%azipFromDir%" %azipFileName%* "%azipToDir%" r0 sl
call arm "%azipFromDir%" %azipFileName%* r0 sl
call go %dirCallZip%

goto end

: end