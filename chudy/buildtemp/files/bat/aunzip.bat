@echo off

if "%~1" == "" goto a1
if "%~2" == "" goto a2
if "%~3" == "" goto a3
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

set aunzipFromDir=%~1
set aunzipToDir=%~2
set aunzipFileName=%~3

echo unzipping "%aunzipFromDir%\%aunzipFileName%" to "%aunzipToDir%"

if not exist "%aunzipToDir%" call md "%aunzipToDir%"
call go "%aunzipToDir%"
call acp "%aunzipFromDir%" %aunzipFileName%* "%aunzipToDir%" r0 sl
call jar xf "%aunzipFromDir%\%aunzipFileName%"
call arm "%aunzipToDir%" %aunzipFileName%* r0 sl

goto end

: end