@echo off
call yodadir
call svn diff m3o\server\src\client m3o\server\src\core m3o\server\src\virtualserver m3o\server\src\domainservice m3o\j2ee\src\application m3o\server\locale\en_US > "C:\Users\Chudy\Desktop\yoda.patch"
if "%mdf%"=="" call explorer "C:\Users\Chudy\Desktop\yoda.patch"
