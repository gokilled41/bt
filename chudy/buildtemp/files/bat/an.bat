@echo off

echo copying dist jars to jboss lib
call al dist in(lib) acp lib

echo=
echo=

echo unzipping vtm3oserver.ear
call arm dd\vtm3oserver sl
call al 'd' vtm3oserver.ear unzip=dd\vtm3oserver
echo updating jars
call al dm in(dd\vtm3oserver\lib) acp dd\vtm3oserver\lib
call al dmj in(dd\vtm3oserver\ejb) acp dd\vtm3oserver\ejb
call al dm in(dd\vtm3oserver\sar) acp dd\vtm3oserver\sar
call al dm in(dd\vtm3oserver\war) acp dd\vtm3oserver\war
echo zipping vtm3oserver.ear
call all dd vtm3oserver zip=dd\vtm3oserver.ear
echo copying vtm3oserver.ear
call all dd vtm3oserver.ear acp 'd'
echo cleaning up vtm3oserver.ear
call arm dd\vtm3oserver sl
call arm dd\vtm3oserver.ear sl

echo=
echo=
