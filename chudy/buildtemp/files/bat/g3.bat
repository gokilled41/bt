@echo off
call btdir
call c
call ant -Dgrep.replacestr.dir=%1 -Dgrep.replacestr.from=%2 -Dgrep.replacestr.to=%3 -Dgrep.replacestr.case=false grep-replacestr-replace-one-time