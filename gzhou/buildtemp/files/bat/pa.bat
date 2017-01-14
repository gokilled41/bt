@echo off

rem use last param to determine debug or not
  set LastBatParam=
  set ADEBUG=false
  if "%DisablePADebug%" == "" call LastBatParam %*
  if "%LastBatParam%" == "-d" set ADEBUG=true
  set LastBatParam=

rem pa operation
  set PAOperation=%~1

rem execute command
  if "%PALogTab%"=="" call palias %*
  if not "%PALogTab%"=="" call palias %* -lt%PALogTab%

rem set debug back
  if "%LastBatParam%" == "-d" set ADEBUG=false

rem run necessary tmp scripts
  rem output to file
    if exist "C:\workspace\buildtemp\files\bat\aoutputtmp.bat" call aoutputtmp
    if exist "C:\workspace\buildtemp\files\bat\aoutputtmp.bat" call bdel aoutputtmp
  rem tmp
    if exist "C:\workspace\buildtemp\files\bat\atmp.bat" call atmp
    if exist "C:\workspace\buildtemp\files\bat\atmp.bat" call bdel atmp
  rem open
    if exist "C:\workspace\buildtemp\files\bat\aopentmp.bat" call aopentmp
    if exist "C:\workspace\buildtemp\files\bat\aopentmp.bat" call bdel aopentmp
  rem open dir
    if exist "C:\workspace\buildtemp\files\bat\aopendirtmp.bat" call aopendirtmp
    if exist "C:\workspace\buildtemp\files\bat\aopendirtmp.bat" call bdel aopendirtmp
  rem open file
    if exist "C:\workspace\buildtemp\files\bat\aopenfiletmp.bat" call aopenfiletmp
    if exist "C:\workspace\buildtemp\files\bat\aopenfiletmp.bat" call bdel aopenfiletmp
  rem go
    if exist "C:\workspace\buildtemp\files\bat\agotmp.bat" call agotmp
    if exist "C:\workspace\buildtemp\files\bat\agotmp.bat" call bdel agotmp
  rem zip
    if "%PAOperation%"=="list" goto azipstart
    if not "%PAOperation%"=="list" goto azipend
      : azipstart
      if exist "C:\workspace\buildtemp\files\bat\aziptmp.bat" call aziptmp
      if exist "C:\workspace\buildtemp\files\bat\aziptmp.bat" call bdel aziptmp
      : azipend
  rem unzip
    if "%PAOperation%"=="list" goto aunzipstart
    if not "%PAOperation%"=="list" goto aunzipend
      : aunzipstart
      if exist "C:\workspace\buildtemp\files\bat\aunziptmp.bat" call aunziptmp
      if exist "C:\workspace\buildtemp\files\bat\aunziptmp.bat" call bdel aunziptmp
      : aunzipend
  rem diffsame
    if exist "C:\workspace\buildtemp\files\bat\adiffsametmp.bat" call adiffsametmp
    if exist "C:\workspace\buildtemp\files\bat\adiffsametmp.bat" call bdel adiffsametmp
