@echo off
call btdir
call c
call ant -Dgrep.replacestr.dir=%1 -Dgrep.replacestr.contains=%2 -Dgrep.replacestr.from=%3 -Dgrep.replacestr.to=%4 -Dgrep.replacestr.case=false grep-replacestr-replace-one-time-contains