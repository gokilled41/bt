@echo off
call yodadir
call svn commit m3o\server\src\client m3o\server\src\core m3o\server\src\virtualserver m3o\server\src\domainservice m3o\j2ee\src\application m3o\server\locale\en_US -m %1 
