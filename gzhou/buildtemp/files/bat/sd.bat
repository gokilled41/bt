@echo off

if "%~1" == "o" goto a1
if "%~1" == "" goto b

: a1
call svn diff > D:\alogs\sd.log
call e D:\alogs\sd.log
goto end

: b
call svn diff
goto end

: end
