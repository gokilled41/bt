d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\m3o\server\src\client
call svn up -r 185102

cd %YODA_HOME%\m3o\server\src\core
call svn up -r 185102

cd %YODA_HOME%\m3o\server\src\feedserver
call svn up -r 185102

cd %YODA_HOME%\m3o\server\src\engine
call svn up -r 185102

cd %YODA_HOME%\m3o\server\src\virtualserver
call svn up -r 185102

cd %YODA_HOME%\m3o\server\src\domainservice
call svn up -r 185102

cd %YODA_HOME%\m3o\j2ee\src\application
call svn up -r 185102
