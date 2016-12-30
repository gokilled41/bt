@echo off
call ant -Dbug.id=%1 make-bug-folder
call all bf %1 d
