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

call afu /l%kkfrom%-%kkto% ar "expand lines" "file timestamp"
call afu /l%kkfrom%-%kkto% ar ExpandLines FileTimestamp
call afu /l%kkfrom%-%kkto% ar "Expand Lines" "File Timestamp"
call afu /l%kkfrom%-%kkto% ar expandLines fileTimestamp
call afu /l%kkfrom%-%kkto% ar elr ftr

goto end

: end
