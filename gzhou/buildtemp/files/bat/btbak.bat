@echo off

echo deleting...
call arm btg\buildtemp sl
call arm btg\typeandrun sl

echo copying bt
call acp bt \svn\bin##src##lib##files btg\buildtemp kd sl

echo copying tar
call acp tar btg\typeandrun sl
