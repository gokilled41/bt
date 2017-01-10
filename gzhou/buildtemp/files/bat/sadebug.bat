@echo off

call satype

if "%SA_TYPE%"=="OI6" goto OI6
if "%SA_TYPE%"=="OI7" goto OI7

: OI6
call al rb1 af dt_socket ar "'rem set'" "set"
call al rb2 af dt_socket ar "'rem set'" "set"
call al rb2 af dt_socket ar "'=8787'" "=18787"
call al rb3 af dt_socket ar "'rem set'" "set"
call al rb3 af dt_socket ar "'=8787'" "=28787"
goto end

: OI7
call al jrb1 af dt_socket ar "'rem # set'" "set"
call al jrb2 af dt_socket ar "'rem # set'" "set"
call al jrb2 af dt_socket ar "'=8787'" "=18787"
call al jrb3 af dt_socket ar "'rem # set'" "set"
call al jrb3 af dt_socket ar "'=8787'" "=28787"
goto end

: end
