d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\bw\src\bc
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/42beta2_patch/bw/src/bc

cd %YODA_HOME%\bw\src\sql
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/42beta2_patch/bw/src/sql
