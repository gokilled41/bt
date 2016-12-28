@echo off
call sc stop OracleServiceORCL
call sc stop OracleOraDb11g_home1TNSListener
call sc stop OracleOraDb11g_home1ClrAgent
call sc stop OracleDBConsoleorcl
call sleep 20
