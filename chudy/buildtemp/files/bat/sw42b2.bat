d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\bw\src\bc
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/42beta2_patch/bw/src/bc

cd %YODA_HOME%\bw\src\sql
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/42beta2_patch/bw/src/sql

cd %YODA_HOME%\m3o\server\src\client
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/42beta2_patch/m3o/server/src/client

cd %YODA_HOME%\m3o\server\src\core
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/42beta2_patch/m3o/server/src/core

cd %YODA_HOME%\m3o\server\src\engine
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/42beta2_patch/m3o/server/src/engine

cd %YODA_HOME%\m3o\server\src\sql
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/42beta2_patch/m3o/server/src/sql

cd %YODA_HOME%\m3o\server\locale\en_US
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/42beta2_patch/m3o/server/locale/en_US
