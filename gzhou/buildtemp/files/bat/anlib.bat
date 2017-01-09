@echo off

rem dir
if "%~1"=="" set ANLIB_DIR=lib
if not "%~1"=="" set ANLIB_DIR=%~1

rem set log tab
  call set PALogTab=4

echo copying dist jars to jboss lib
  call al dist in(lib) acp "%ANLIB_DIR%"

  call anl
  call anl
