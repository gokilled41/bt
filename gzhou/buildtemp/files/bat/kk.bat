@echo off

if "%1" == "" goto a1
if not "%1" == "" goto b

: a1
echo kk from [to]
goto end

: b

if not "%1" == "" set kkfrom=%1
set kkto=%1
if not "%2" == "" set kkto=%2

call afu /l%kkfrom%-%kkto% ar "sort type" "generic parameter"
call afu /l%kkfrom%-%kkto% ar SortType GenericParameter
call afu /l%kkfrom%-%kkto% ar "Sort Type" "Generic Parameter"
call afu /l%kkfrom%-%kkto% ar sortType genericParameter
call afu /l%kkfrom%-%kkto% ar str gpr

goto end

: end
