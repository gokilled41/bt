d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\m3o\server\src\client
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/6.0.0.x/m3o/server/src/client

cd %YODA_HOME%\m3o\server\src\virtualserver
rem call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/6.0.0.x/m3o/server/src/virtualserver

cd %YODA_HOME%\m3o\server\src\domainservice
rem call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/6.0.0.x/m3o/server/src/domainservice

cd %YODA_HOME%\m3o\j2ee\src\application
rem call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/6.0.0.x/m3o/j2ee/src/application

cd %YODA_HOME%\m3o\server\locale\en_US
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/6.0.0.x/m3o/server/locale/en_US
