@echo off

if "%~1" == "o" goto a1
if "%~1" == "" goto b

: a1
call svn st > D:\alogs\st.log
call uedit D:\alogs\st.log
goto end

: b
call svn st
goto end

: end
