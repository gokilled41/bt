@echo off

call btenv

echo deleting...
call arm %btdir%\buildtemp sl
call arm %btdir%\typeandrun sl

echo copying bt
call acp bt \.svn\(bin\files\src\lib) %btdir%\buildtemp kd sl

echo copying tar
call acp tar %btdir%\typeandrun sl
