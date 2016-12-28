d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\bw\src\bc
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/bw/src/bc

cd %YODA_HOME%\bw\src\sql
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/bw/src/sql

cd %YODA_HOME%\m3o\server\src\engine
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/m3o/server/src/engine
