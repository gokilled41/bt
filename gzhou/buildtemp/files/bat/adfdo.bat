@echo off
call set adfCallDir=%cd%
call acp "%~1" btsvn/diff
call go btsvn/diff sl
call svn add "%~3"
call svn commit -m "p1"
call acp "%~2" btsvn/diff "##%~3" ov
call svn diff > D:\\alogs\\svn.diff
call svn revert -R *
call svn del "%~3"
call svn commit -m "p1-del"
call go %adfCallDir%