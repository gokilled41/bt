@echo off

rem pa operation
  set PAOperation=%~1

rem use last param to determine debug or not
  set LastBatParam=
  set ADEBUG=false
  if "%DisablePADebug%" == "" call LastBatParam %*
  if "%LastBatParam%" == "-d" set ADEBUG=true
  set LastBatParam=

rem execute command
  : exec_acu
  call acustom %*

rem set debug back
  if "%LastBatParam%" == "-d" set ADEBUG=false

rem run necessary tmp scripts
  rem output to file
    if exist "C:\workspace\buildtemp\files\bat\aoutputtmp.bat" call aoutputtmp
    if exist "C:\workspace\buildtemp\files\bat\aoutputtmp.bat" call bdel aoutputtmp
  rem tmp
    if exist "C:\workspace\buildtemp\files\bat\atmp.bat" call atmp
    if exist "C:\workspace\buildtemp\files\bat\atmp.bat" call bdel atmp
  rem env
    if exist "C:\workspace\buildtemp\files\bat\aenvtmp.bat" call aenvtmp
    if exist "C:\workspace\buildtemp\files\bat\aenvtmp.bat" call bdel aenvtmp
  rem diff
    if exist "C:\workspace\buildtemp\files\bat\adifftmp.bat" call adifftmp
    if exist "C:\workspace\buildtemp\files\bat\adifftmp.bat" call bdel adifftmp
  rem edit
    if exist "C:\workspace\buildtemp\files\bat\aedittmp.bat" call aedittmp
    if exist "C:\workspace\buildtemp\files\bat\aedittmp.bat" call bdel aedittmp
