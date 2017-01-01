@echo off
call btdir
call c
call ant -Dgrep.replacestr.dir=%1 -Dgrep.replacestr.from=%2 grep-replacestr-find-one-time