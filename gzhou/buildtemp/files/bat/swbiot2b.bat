d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\unbundled\af\java\dataflow\client
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/unbundled/af/java/dataflow/client

cd %YODA_HOME%\unbundled\af\java\dataflow\framework
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/unbundled/af/java/dataflow/framework

cd %YODA_HOME%\unbundled\af\java\dataflow\locale
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda/unbundled/af/java/dataflow/locale
