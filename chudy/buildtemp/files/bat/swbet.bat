d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\m3o\bpa\emf\server\src\modules
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/bpa/emf/server/src/modules

cd %YODA_HOME%\m3o\j2ee\src\application
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/j2ee/src/application

cd %YODA_HOME%\m3o\server\devtests\domainservice\junitee\domainservice
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/server/devtests/domainservice/junitee/domainservice

cd %YODA_HOME%\m3o\server\locale\en_US
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/server/locale/en_US

cd %YODA_HOME%\m3o\server\src\client
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/server/src/client

cd %YODA_HOME%\m3o\server\src\core
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/server/src/core

cd %YODA_HOME%\m3o\server\src\domainservice
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/server/src/domainservice

cd %YODA_HOME%\m3o\server\src\engine
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/server/src/engine

cd %YODA_HOME%\m3o\server\src\virtualserver
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/server/src/virtualserver

