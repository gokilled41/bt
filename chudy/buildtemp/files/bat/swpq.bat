D:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\unbundled\af\java\dataflow
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/oi60_parquet/unbundled/af/java/dataflow

cd %YODA_HOME%\unbundled\apps\data_flow
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/oi60_parquet/unbundled/apps/data_flow

