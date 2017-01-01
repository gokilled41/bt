@echo off
call btdir
call c
call ant -Dgrep.replacestr.dir=%1 -Dgrep.replacestr.contains=%2 -Dgrep.replacestr.from=%3 grep-replacestr-find-one-time-contains