d:

cd D:\jedi\yoda\workflow\src\common
rem call ant clean
call ant nosql

cd D:\jedi\yoda\workflow\src\client
rem call ant clean
call ant

cd D:\jedi\yoda\workflow\src\taskmanager
rem call ant clean
call ant

d:
cd C:\workspace\buildtemp
call ant wf
