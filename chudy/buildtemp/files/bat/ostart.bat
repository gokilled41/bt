@echo off
call sc start OracleServiceORCL
call sc start OracleOraDb11g_home1TNSListener
call sc start OracleOraDb11g_home1ClrAgent
call sc start OracleDBConsoleorcl
call sleep 40
