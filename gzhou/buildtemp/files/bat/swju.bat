d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\build\dtd
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o42_JBossUpgrade/build/dtd

cd %YODA_HOME%\build\properties
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o42_JBossUpgrade/build/properties

cd %YODA_HOME%\bw\installer
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o42_JBossUpgrade/bw/installer

