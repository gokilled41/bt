@echo off
call btenv
call btbak
call go %btdir%
call gadd
call echo committing
call btcommit > D:\alogs\btcommit.log
