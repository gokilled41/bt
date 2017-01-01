d:
set YODA_HOME_3101=D:\jedi\yoda_3101

cd %YODA_HOME_3101%\bw\src\fc
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/fc_3101/fc

cd %YODA_HOME_3101%\m3o\workflow\src
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/wfsrc_3101/src

cd %YODA_HOME_3101%\m3o\j2ee\src\application
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/j2ee_app_3101/application

cd %YODA_HOME_3101%\m3o\j2ee\src\connectors
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/j2ee_connectors_3101/connectors

cd %YODA_HOME_3101%\m3o\server\src\engine
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/engine_3101/engine

cd %YODA_HOME_3101%\m3o\server\src\core
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/core_3101

cd %YODA_HOME_3101%\m3o\server\src\sql
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/sql_3101/sql

cd %YODA_HOME_3101%\m3o\server\locale\en_US
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/mslocale_3101/en_US
