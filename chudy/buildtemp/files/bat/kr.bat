@echo off
call c
call yenv
if "%YODA_TYPE%"=="main" call rj
if "%YODA_TYPE%"=="sjb" call rw
