@echo off

call btenv

call al %btdirfrom%\buildtemp\files\bat .bat np acp rn\bat ##{n}-2.bat

call al bt\files\bat .bat np acp rn\bat

if "%btenv%"=="gzhou" call kbat
if "%btenv%"=="chudy" call kbat2

call al rn -2 ds
