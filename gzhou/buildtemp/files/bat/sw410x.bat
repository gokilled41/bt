d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\bw\src\bc
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/4.1.0.x/bw/src/bc

cd %YODA_HOME%\m3o\server\src\engine
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/4.1.0.x/m3o/server/src/engine
