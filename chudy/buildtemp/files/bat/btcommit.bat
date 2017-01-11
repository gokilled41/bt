@echo off
call btenv
call go %btdir%
call git commit -a -m update
call git push
