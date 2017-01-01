c:
cd C:\vtba\jboss\bin
set VTBA_HOME=C:\vtba
set JBOSS_HOME=%VTBA_HOME%\jboss
set ESMS=%VTBA_HOME%\esms
call run -c vtba -b 0.0.0.0
