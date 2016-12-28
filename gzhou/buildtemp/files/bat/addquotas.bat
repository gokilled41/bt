@echo off
set addquotascut=%addquotasfrom:~1,-1%
if "%addquotascut%" == %addquotasfrom% set addquotasto=%addquotascut%
if not "%addquotascut%" == %addquotasfrom% set addquotasto="%addquotasfrom%"
