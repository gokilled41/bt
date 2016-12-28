C:
cd C:\workspace
call rar a buildtemp.zip buildtemp > D:\to-delete\buildtemp.log
call move buildtemp.zip D:\share\buildtemp

call btdir
call ant hz-backup-udisk2
