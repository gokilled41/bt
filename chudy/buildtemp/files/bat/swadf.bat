d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\m3o\server\src\client
rem call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/Analytics_DataFlow_preview/m3o/server/src/client

cd %YODA_HOME%\m3o\server\src\virtualserver
rem call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/Analytics_DataFlow_preview/m3o/server/src/virtualserver

cd %YODA_HOME%\m3o\server\src\domainservice
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/Analytics_DataFlow_preview/m3o/server/src/domainservice
