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

call afu /l%kkfrom%-%kkto% ar "delete same" "mark occurrence"
call afu /l%kkfrom%-%kkto% ar DeleteSame MarkOccurrence
call afu /l%kkfrom%-%kkto% ar "Delete Same" "Mark Occurrence"
call afu /l%kkfrom%-%kkto% ar deleteSame markOccurrence
call afu /l%kkfrom%-%kkto% ar dsr mor

goto end

: end
