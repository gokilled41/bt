d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\unbundled\af\java\dataflow\modules
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/apps/major-minor/Apps_3.2Beta/unbundled/af/java/dataflow/modules

cd %YODA_HOME%\unbundled\af\java\dataflow\tools
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/apps/major-minor/Apps_3.2Beta/unbundled/af/java/dataflow/tools

cd %YODA_HOME%\unbundled\apps\data_flow
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/apps/major-minor/Apps_3.2Beta/unbundled/apps/data_flow
