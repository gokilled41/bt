call c
set VTBA_HOME=C:\vtba
set JBOSS_HOME=%VTBA_HOME%\jboss
call %JBOSS_HOME%\bin\run.bat -c vtba -b 0.0.0.0
