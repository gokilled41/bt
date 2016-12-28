@echo off

set batparams=%1
shift

: a

if "%1" == "" goto end
set batparams=%batparams% %1
shift

goto a

: end
