@echo off

call ye

if "%YODA_TYPE%"=="main" set ANLIB_DIR_DEFAULT=lib
if "%YODA_TYPE%"=="sjb" set ANLIB_DIR_DEFAULT=jmvv

rem dir
if "%~1"=="" set ANLIB_DIR=%ANLIB_DIR_DEFAULT%
if not "%~1"=="" set ANLIB_DIR=%~1

rem set log tab
  call set PALogTab=4

echo copying dist jars to jboss lib
  call al dist in(lib) acp "%ANLIB_DIR%"

  call anl
  call anl
