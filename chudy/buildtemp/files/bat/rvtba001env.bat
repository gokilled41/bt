@echo off
rem -------------------------------------------------------------------------
rem Set environment variables
rem -------------------------------------------------------------------------

set VTBA_HOME=E:\vtba001
set JBOSS_HOME=%VTBA_HOME%\jboss
set ESMS=%VTBA_HOME%\esms
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_51
set Path=%JAVA_HOME%\bin;C:\Program Files\Common Files\NetSarang;C:\oracle\product\10.2.0\db_1\bin;C:\Oracle\product\10.1.0\Client_1\bin;%SystemRoot%\system32;%SystemRoot%;%SystemRoot%\System32\Wbem;%SVN_HOME%\bin;C:\Program Files\NTP\bin;C:\Program Files\Subversion;C:\Program Files\TortoiseSVN\bin;%BAT_HOME%;%ANT_HOME%\bin;%VTBA_HOME%\esms\bin;%VTBA_HOME%\bin;%WAS_HOME%\bin;C:\Program Files\Common Files\TTKN\Bin;C:\Program Files\Microsoft SQL Server\80\Tools\Binn\;C:\Program Files\Microsoft SQL Server\90\Tools\binn\;C:\Program Files\Common Files\Thunder Network\KanKan\Codecs

rem -------------------------------------------------------------------------
rem Go to directory of JBoss bin
rem -------------------------------------------------------------------------

e:
cd %VTBA_HOME%
