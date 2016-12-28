@echo off
call btdir
call c
call ant -Dgrep.replacestr.dir=%1 -Dgrep.replacestr.from=%2 -Dgrep.replacestr.encoding=gbk grep-replacestr-find-one-time-encoding