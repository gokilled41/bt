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

call afu /l%kkfrom%-%kkto% ar "expand lines" "go dir"
call afu /l%kkfrom%-%kkto% ar ExpandLines GoDir
call afu /l%kkfrom%-%kkto% ar "Expand Lines" "Go Dir"
call afu /l%kkfrom%-%kkto% ar expandLines goDir
call afu /l%kkfrom%-%kkto% ar elr gdr

goto end

: end
