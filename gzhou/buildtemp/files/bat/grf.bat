@echo off

if "%1" == "" goto a1
if "%2" == "" goto a2
if "%3" == "" goto a3
if not "%1" == "" goto b

: a1
echo please give one dir/file name or type and run alias.
goto end

: a2
echo please give one value replace from.
goto end

: a3
echo please give one value replace to.
goto end

: b
echo replace from "%2" to "%3" in "%1" only in files.
call arun -Dgrep.replacestr.dir=%1 -Dgrep.replacestr.from=%2 -Dgrep.replacestr.to=%3 -Dgrep.replacestr.case=false grep-replacestr-replace-one-time-onlyfile
goto end

: end
