d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\m3o\server\src\client
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/42beta1_patch/m3o/server/src/client

cd %YODA_HOME%\m3o\server\src\domainservice
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/42beta1_patch/m3o/server/src/domainservice

cd %YODA_HOME%\m3o\server\locale\en_US
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/m3o/patches/42beta1_patch/m3o/server/locale/en_US
