@echo off
call btdir
call batparams %*
call ant -Dtext="%batparams%" create-bug-detail
call af dsb "%batparams:~0,12%" f