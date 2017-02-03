@echo off

rem ENV
call tmenv

rem RUN
call tmdir
cd bin
call catalina.bat run
