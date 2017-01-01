@echo off
echo Enable PA debug
call set DisablePADebug=
if not "%~1" == "" set PADebugPort=%~1
