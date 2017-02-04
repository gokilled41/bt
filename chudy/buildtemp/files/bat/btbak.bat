@echo off

call btenv

echo deleting...
call arm %btdir%\buildtemp sl
call arm %btdir%\typeandrun sl
call arm %btdir%\crm sl

echo copying bt
call acp bt \.svn\Wmic\(bin\(files\class)\src\lib)\(src/class)\xtj\s10 %btdir%\buildtemp kd sl

echo copying tar
call acp tar %btdir%\typeandrun sl

echo copying others
call acp "dd\PA Operations.txt" %btdir%\others kd sl

echo copying crm
call acp kw %btdir%\crm\work kd sl
call acp kg \.git %btdir%\crm\code kd sl
