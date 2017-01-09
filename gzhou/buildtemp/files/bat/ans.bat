@echo off

call ye

set ANS_DIR_TMP=alogs

if "%YODA_TYPE%"=="main" goto main
if "%YODA_TYPE%"=="sjb" goto sjb

: main
call set ANS_DIR_DEFAULT='d'
call set ANS_DIR_LIB=%ANS_DIR_TMP%\vtm3oserver\lib
call set ANS_DIR_EJB=%ANS_DIR_TMP%\vtm3oserver\ejb
call set ANS_DIR_SAR=%ANS_DIR_TMP%\vtm3oserver\sar
call set ANS_DIR_WAR=%ANS_DIR_TMP%\vtm3oserver\war

goto go

: sjb
call set ANS_DIR_DEFAULT='jd'
call set ANS_DIR_LIB=%ANS_DIR_TMP%\vtm3oserver\lib
call set ANS_DIR_EJB=%ANS_DIR_TMP%\vtm3oserver
call set ANS_DIR_SAR=%ANS_DIR_TMP%\vtm3oserver
call set ANS_DIR_WAR=%ANS_DIR_TMP%\vtm3oserver

goto go

: go
rem dir
if "%~1"=="" set ANS_DIR=%ANS_DIR_DEFAULT%
if not "%~1"=="" set ANS_DIR=%~1

rem set log tab
  call set PALogTab=4

echo unzipping vtm3oserver.ear
  call arm %ANS_DIR_TMP%\vtm3oserver sl
  call al "%ANS_DIR%" vtm3oserver.ear unzip=%ANS_DIR_TMP%\vtm3oserver sl
  call anl
echo updating jars
  call al dm in(%ANS_DIR_LIB%) acp %ANS_DIR_LIB% os(%ANS_DIR_TMP%\vtm3oserver)
  call al dmj in(%ANS_DIR_EJB%) acp %ANS_DIR_EJB% os(%ANS_DIR_TMP%\vtm3oserver)
  call al dm in(%ANS_DIR_SAR%) acp %ANS_DIR_SAR% os(%ANS_DIR_TMP%\vtm3oserver)
  call al dm in(%ANS_DIR_WAR%) acp %ANS_DIR_WAR% os(%ANS_DIR_TMP%\vtm3oserver)
  call anl

rem whether need zip and copy
  call aexist %ANS_DIR_TMP%\vtm3oserver\summary.txt
  if "%AEXIST%"=="true" call set AN_HAS_UPDATE=true
  if "%AEXIST%"=="" call set AN_HAS_UPDATE=
  call arm %ANS_DIR_TMP%\vtm3oserver\summary.txt sl

echo zipping vtm3oserver.ear
  if "%AN_HAS_UPDATE%"=="true" call all dd vtm3oserver zip=%ANS_DIR_TMP%\vtm3oserver.ear sl
  if "%AN_HAS_UPDATE%"=="" echo vtm3oserver.ear has no updates
  call anl
echo copying vtm3oserver.ear
  if "%AN_HAS_UPDATE%"=="true" call all dd vtm3oserver.ear acp "%ANS_DIR%"
  if "%AN_HAS_UPDATE%"=="" echo no need copy
  call anl
echo cleaning up vtm3oserver.ear
  call arm %ANS_DIR_TMP%\vtm3oserver sl
  call arm %ANS_DIR_TMP%\vtm3oserver.ear sl
  call set AN_HAS_UPDATE=
  call set ANS_DIR=
  call set AEXIST=

  call anl
  call anl
