call btenv
call btbak
call go %btdir%
call gst > D:\alogs\gst.log
call agit add
call git commit -a -m update
call git push