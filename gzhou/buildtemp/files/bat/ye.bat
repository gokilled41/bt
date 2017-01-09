@echo off

set JEDI=D:\jedi

if exist "%JEDI%\yoda_sjb" set YODA_TYPE=main
if exist "%JEDI%\yoda_main" set YODA_TYPE=sjb

set JEDI=