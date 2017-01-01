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

call afu /l%kkfrom%-%kkto% ar "expand lines" "zip operations"
call afu /l%kkfrom%-%kkto% ar ExpandLines ZipOperations
call afu /l%kkfrom%-%kkto% ar "Expand Lines" "Zip Operations"
call afu /l%kkfrom%-%kkto% ar expandLines zipOperations
call afu /l%kkfrom%-%kkto% ar elr zor

goto end

: end
