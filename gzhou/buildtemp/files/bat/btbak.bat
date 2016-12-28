@echo off
echo deleting...
call arm D:\huazhi\projects\git\bt\gzhou\buildtemp > D:\huazhi\projects\git\bt\gzhou\del_bt.log
call arm D:\huazhi\projects\git\bt\gzhou\typeandrun > D:\huazhi\projects\git\bt\gzhou\del_tar.log
echo copying bt
call acp bt \svn\bin##src##lib##files D:\huazhi\projects\git\bt\gzhou\buildtemp kd > D:\huazhi\projects\git\bt\gzhou\acp_bt.log
echo copying tar
call acp tar D:\huazhi\projects\git\bt\gzhou\typeandrun > D:\huazhi\projects\git\bt\gzhou\acp_tar.log
call arm D:\huazhi\projects\git\bt\gzhou log r0 > "C:\Users\gzhou\Local Settings\Temp\arm_bt_log.log"