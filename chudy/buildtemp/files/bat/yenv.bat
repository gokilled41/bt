@echo off

if exist "D:\jedi\yoda_sjb" set YODA_TYPE=main
if exist "D:\jedi\yoda_main" set YODA_TYPE=sjb

if "%YODA_TYPE%"=="main" call benv
if "%YODA_TYPE%"=="sjb" call benvju

call env
