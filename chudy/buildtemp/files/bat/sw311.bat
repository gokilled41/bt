d:
set YODA_HOME_311LA=D:\jedi\yoda

cd %YODA_HOME_311LA%\m3o\server\src\core
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_tags/m3o/maintenance/3.1.1LA/m3o/server/src/core

cd %YODA_HOME_311LA%\m3o\server\src\virtualserver
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_tags/m3o/maintenance/3.1.1LA/m3o/server/src/virtualserver
