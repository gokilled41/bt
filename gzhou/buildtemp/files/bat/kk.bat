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

call afu /l%kkfrom%-%kkto% ar "file timestamp" "output summary"
call afu /l%kkfrom%-%kkto% ar FileTimestamp OutputSummary
call afu /l%kkfrom%-%kkto% ar "File Timestamp" "Output Summary"
call afu /l%kkfrom%-%kkto% ar fileTimestamp outputSummary
call afu /l%kkfrom%-%kkto% ar ftr osr

goto end

: end
