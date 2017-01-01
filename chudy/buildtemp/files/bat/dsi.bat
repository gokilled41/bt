@echo off
call yodadir
cd m3o\server\src\client
call svn info

@echo off
call yodadir
cd m3o\server\src\core
call svn info

@echo off
call yodadir
cd m3o\server\src\virtualserver
call svn info

@echo off
call yodadir
cd m3o\server\src\domainservice
call svn info

@echo off
call yodadir
cd m3o\j2ee\src\application
call svn info

@echo off
call yodadir
cd m3o\server\locale\en_US
call svn info

