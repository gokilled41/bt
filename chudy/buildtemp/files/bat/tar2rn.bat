@echo off

call btenv
call al %btdir% Config.ini/type acp rn ##Config-2.ini
call al tar acp rn

if "%btenv%"=="gzhou" call ktar
if "%btenv%"=="chudy" call ktar2
