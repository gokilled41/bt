@echo off
if "%~2" == "" set jsm_id=1
if not "%~2" == "" set jsm_id=%2
if not exist "D:\jedi\yoda\export\home\jboss\server\vtba\log" mkdir D:\jedi\yoda\export\home\jboss\server\vtba\log
echo jstack %1 to file D:\jedi\yoda\export\home\jboss\server\vtba\log\jboss%jsm_id%.txt
call jstack %1 > D:\jedi\yoda\export\home\jboss\server\vtba\log\jboss%jsm_id%.txt