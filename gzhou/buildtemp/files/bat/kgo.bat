@echo off
call btenv
call btbak
call go %btdir%
call gadd
call echo commting
call btcommit > D:\alogs\btcommit.log
