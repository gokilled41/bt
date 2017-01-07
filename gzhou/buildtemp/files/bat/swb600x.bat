d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\m3o\server\src\client
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/server/src/client

cd %YODA_HOME%\m3o\server\src\virtualserver
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/server/src/virtualserver

cd %YODA_HOME%\m3o\server\src\domainservice
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/server/src/domainservice

cd %YODA_HOME%\m3o\j2ee\src\application
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/j2ee/src/application

cd %YODA_HOME%\m3o\server\locale\en_US
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/server/locale/en_US
