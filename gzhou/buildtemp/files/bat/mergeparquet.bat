set YODA_HOME=D:\jedi\yoda
call svn merge -r 214663:HEAD http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/unbundled/af/java/dataflow  %YODA_HOME%\unbundled\af\java\dataflow
call svn merge -r 214663:HEAD http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/unbundled/apps/data_flow  %YODA_HOME%\unbundled\apps\data_flow
