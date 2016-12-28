d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\unbundled\af\java\dataflow
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/apps/major-minor/DashboardBuilder_v31GA/unbundled/af/java/dataflow
cd %YODA_HOME%\unbundled\apps\data_flow
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_release_branches/apps/major-minor/DashboardBuilder_v31GA/unbundled/apps/data_flow
