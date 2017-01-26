@echo off

call yenv

if "%YODA_TYPE%"=="main" call goto OI6
if "%YODA_TYPE%"=="sjb" call goto OI7

: OI6
call al rb af dt_socket ar "'rem set'" "set"
call al rb af dt_socket ar "'suspend=y'" "suspend=n"
goto end

: OI7
call al jrb af dt_socket ar "'rem # set'" "set"
call al jrb af dt_socket ar "'suspend=y'" "suspend=n"
goto end

: end
