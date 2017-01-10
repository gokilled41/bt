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

call afu /l%kkfrom%-%kkto% ar "no path" "full path"
call afu /l%kkfrom%-%kkto% ar NoPath FullPath
call afu /l%kkfrom%-%kkto% ar "No Path" "Full Path"
call afu /l%kkfrom%-%kkto% ar noPath fullPath
call afu /l%kkfrom%-%kkto% ar npr fpr

goto end

: end
