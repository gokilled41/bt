call btenv
call btbak
call go %btdir%
call git commit -a -m update
call git push