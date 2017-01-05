@echo off

call btenv
call al %btdirfrom% Config.ini/type acp rn ##Config-2.ini
call al tar acp rn

if "%btenv%"=="gzhou" call ktar
if "%btenv%"=="chudy" call ktar2
