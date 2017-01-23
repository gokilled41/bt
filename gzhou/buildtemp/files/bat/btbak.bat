@echo off

call btenv

echo deleting...
call arm %btdir%\buildtemp sl
call arm %btdir%\typeandrun sl

echo copying bt
call acp bt \.svn\Wmic\(bin\files\src\lib)\(src/class)\s10 %btdir%\buildtemp kd sl

echo copying tar
call acp tar %btdir%\typeandrun sl

echo copying others
call acp "dd\PA Operations.txt" %btdir%\others kd sl
