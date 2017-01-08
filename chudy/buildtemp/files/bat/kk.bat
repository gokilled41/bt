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

call afu /l%kkfrom%-%kkto% ar "file timestamp" "list condition"
call afu /l%kkfrom%-%kkto% ar FileTimestamp ListCondition
call afu /l%kkfrom%-%kkto% ar "File Timestamp" "List Condition"
call afu /l%kkfrom%-%kkto% ar fileTimestamp listCondition
call afu /l%kkfrom%-%kkto% ar ftr lcr

goto end

: end
