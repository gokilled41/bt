@echo off

rem use last param to determine debug or not
  set LastBatParam=
  set ADEBUG=false
  if "%DisablePADebug%" == "" call LastBatParam %*
  if "%LastBatParam%" == "-d" set ADEBUG=true
  set LastBatParam=

rem execute command
  call palias %*

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
