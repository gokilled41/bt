@echo off

if "%~1" == "" goto a1
if "%~2" == "" goto a2
if not "%~1" == "" goto b

: a1
echo please give one dir/file name or type and run alias.
goto end

: a2
echo please give one search key.
goto end

: b
echo find "%~2" in "%~1" only in files.
call arun -Dgrep.replacestr.dir=%1 -Dgrep.replacestr.from=%2 grep-replacestr-find-one-time-onlyfile
goto end

: end
