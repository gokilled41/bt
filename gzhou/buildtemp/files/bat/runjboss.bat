@echo off
call env

rem -------------------------------------------------------------------------
rem Go to directory of JBoss bin
rem -------------------------------------------------------------------------

d:
cd %JBOSS_HOME%\bin

rem -------------------------------------------------------------------------
rem Start the JBoss
rem -------------------------------------------------------------------------

call run -c vtba -b 0.0.0.0