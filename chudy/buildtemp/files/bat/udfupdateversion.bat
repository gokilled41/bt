@echo off

if "%1" == "" goto a1
if "%2" == "" goto a2
if not "%1" == "" goto b

: a1
echo please give one version from.
goto end

: a2
echo please give one version to.
goto end

: b

call gr udfml "<version>%1</version>" "<version>%2</version>"
call gr udfml "<property name=""df.module.version"" value=""%1"" />" "<property name=""df.module.version"" value=""%2"" />"
call gr udfml "version=%1" "version=%2"
call gr udft %1 %2
call gr udfap %1 %2
call gr udfab %1 %2
call gr udfasdk %1 %2

goto end

: end
