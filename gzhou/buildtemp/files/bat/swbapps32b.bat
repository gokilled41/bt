d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\unbundled\af\java\dataflow\modules
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/unbundled/af/java/dataflow/modules

cd %YODA_HOME%\unbundled\af\java\dataflow\tools
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/unbundled/af/java/dataflow/tools

cd %YODA_HOME%\unbundled\apps\data_flow
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/unbundled/apps/data_flow
