@echo off

rem set log tab
  call set PALogTab=4

echo copying dist jars to jboss lib
  call al dist in(lib) acp lib

  call anl
  call anl

echo unzipping vtm3oserver.ear
  call arm dd\vtm3oserver sl
  call al 'd' vtm3oserver.ear unzip=dd\vtm3oserver sl
  call anl
echo updating jars
  call al dm in(dd\vtm3oserver\lib) acp dd\vtm3oserver\lib os(dd\vtm3oserver)
  call al dmj in(dd\vtm3oserver\ejb) acp dd\vtm3oserver\ejb os(dd\vtm3oserver)
  call al dm in(dd\vtm3oserver\sar) acp dd\vtm3oserver\sar os(dd\vtm3oserver)
  call al dm in(dd\vtm3oserver\war) acp dd\vtm3oserver\war os(dd\vtm3oserver)
  call anl

rem whether need zip and copy
  if exist "C:\Users\Chudy\Desktop\vtm3oserver\summary.txt" call set AN_HAS_UPDATE=true

echo zipping vtm3oserver.ear
  if "%AN_HAS_UPDATE%"=="true" call all dd vtm3oserver zip=dd\vtm3oserver.ear sl
  if "%AN_HAS_UPDATE%"=="" echo vtm3oserver.ear has no updates
  call anl
echo copying vtm3oserver.ear
  if "%AN_HAS_UPDATE%"=="true" call all dd vtm3oserver.ear acp 'd'
  if "%AN_HAS_UPDATE%"=="" echo no need copy
  call anl
echo cleaning up vtm3oserver.ear
  rem call arm dd\vtm3oserver sl
  call arm dd\vtm3oserver.ear sl

  call anl
  call anl
