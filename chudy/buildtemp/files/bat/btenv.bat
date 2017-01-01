@echo off

if not "%btenv%"=="" goto end

if exist C:\Users\Chudy set btenv=chudy
if exist C:\Users\gzhou set btenv=gzhou

echo btenv=%btenv%

if "%btenv%"=="chudy" set btdir=btc
if "%btenv%"=="gzhou" set btdir=btg

:end
