@echo off
call yodadir
cd m3o\server\src\client
call ant

@echo off
call yodadir
cd m3o\server\src\core
call ant

@echo off
call yodadir
cd m3o\server\src\virtualserver
call ant

@echo off
call yodadir
cd m3o\server\src\domainservice
call ant

@echo off
call yodadir
cd m3o\j2ee\src\application
call ant

@echo off
call yodadir
cd m3o\server\locale\en_US
call ant

