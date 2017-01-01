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

call afu /l%kkfrom%-%kkto% ar "expand lines" "operate lines"
call afu /l%kkfrom%-%kkto% ar ExpandLines OperateLines
call afu /l%kkfrom%-%kkto% ar "Expand Lines" "Operate Lines"
call afu /l%kkfrom%-%kkto% ar expandLines operateLines
call afu /l%kkfrom%-%kkto% ar elr olr

goto end

: end
