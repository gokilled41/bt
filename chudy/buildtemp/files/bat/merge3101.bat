set YODA_HOME_3101=D:\jedi\yoda
call svn merge -r 142322:HEAD http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/fc_3101/fc  %YODA_HOME_3101%\bw\src\fc
call svn merge -r 142322:HEAD http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/wfsrc_3101/src  %YODA_HOME_3101%\m3o\workflow\src
call svn merge -r 142322:HEAD http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/j2ee_app_3101/application  %YODA_HOME_3101%\m3o\j2ee\src\application
call svn merge -r 142318:HEAD http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/engine_3101/engine  %YODA_HOME_3101%\m3o\server\src\engine
call svn merge -r 142322:HEAD http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/mslocale_3101/en_US  %YODA_HOME_3101%\m3o\server\locale\en_US
call svn merge -r 142322:HEAD http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/core_3101  %YODA_HOME_3101%\m3o\server\src\core
call svn merge -r 142322:HEAD http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/sql_3101/sql  %YODA_HOME_3101%\m3o\server\src\sql
call svn merge -r 142322:HEAD http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/j2ee_connectors_3101/connectors  %YODA_HOME_3101%\m3o\j2ee\src\connectors
call svn merge -r 142322:HEAD http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o32_simpleTimer/cf_3101/cf/cf  %YODA_HOME_3101%\bw\src\cf
