d:
set YODA_HOME_3101=D:\jedi\yoda

cd %YODA_HOME_3101%\bw\src\fc
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/3.1.0.x/bw/src/fc

cd %YODA_HOME_3101%\m3o\workflow\src
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/3.1.0.x/m3o/workflow/src

cd %YODA_HOME_3101%\m3o\j2ee\src\application
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/3.1.0.x/m3o/j2ee/src/application

cd %YODA_HOME_3101%\m3o\j2ee\src\connectors
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/3.1.0.x/m3o/j2ee/src/connectors

cd %YODA_HOME_3101%\m3o\server\src\engine
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/3.1.0.x/m3o/server/src/engine

cd %YODA_HOME_3101%\m3o\server\src\core
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/3.1.0.x/m3o/server/src/core

cd %YODA_HOME_3101%\m3o\server\src\sql
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/3.1.0.x/m3o/server/src/sql

cd %YODA_HOME_3101%\m3o\server\locale\en_US
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/3.1.0.x/m3o/server/locale/en_US
