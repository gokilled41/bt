d:
set YODA_HOME=D:\jedi\yoda

cd %YODA_HOME%\bw\src\clusterservice
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/bw/src/clusterservice

cd %YODA_HOME%\m3o\server\src\client
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/server/src/client

cd %YODA_HOME%\m3o\server\src\core
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/server/src/core

cd %YODA_HOME%\m3o\server\src\virtualserver
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/server/src/virtualserver

cd %YODA_HOME%\m3o\server\src\domainservice
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/server/src/domainservice

cd %YODA_HOME%\m3o\server\src\feedserver
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/server/src/feedserver

cd %YODA_HOME%\m3o\j2ee\src\application
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/j2ee/src/application

cd %YODA_HOME%\m3o\j2ee\src\ear
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/j2ee/src/ear

cd %YODA_HOME%\m3o\j2ee\src\servlet
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/j2ee/src/servlet

cd %YODA_HOME%\m3o\server\locale\en_US
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/server/locale/en_US

cd %YODA_HOME%\esms\src\m3omodules\querycomponent
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/esms/src/m3omodules/querycomponent

cd %YODA_HOME%\m3o\server\devtests\domainservice\junitee\domainservice
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/server/devtests/domainservice/junitee/domainservice

cd %YODA_HOME%\m3o\server\devtests\feedserver\junitee
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/server/devtests/feedserver/junitee

cd %YODA_HOME%\m3o\server\devtests\tools
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/server/devtests/tools

cd %YODA_HOME%\m3o\ui\src\m3o\src\com\vitria\m3oui\virtualserver
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/ui/src/m3o/src/com/vitria/m3oui/virtualserver

cd %YODA_HOME%\m3o\ui\src\m3o\webapp\conf\locale\en_US
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/ui/src/m3o/webapp/conf/locale/en_US

cd %YODA_HOME%\m3o\ui\src\m3o\webapp\core\com\vitria\m3oui\domainservice
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/ui/src/m3o/webapp/core/com/vitria/m3oui/domainservice

cd %YODA_HOME%\m3o\ui\src\m3o\webapp\core\com\vitria\m3oui\virtualserver
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/ui/src/m3o/webapp/core/com/vitria/m3oui/virtualserver

cd %YODA_HOME%\m3o\ui\src\m3o\webapp\workspace\com\vitria\m3oui\ws\environment\view
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/branches/m3o41_FeedServerHA/m3o/ui/src/m3o/webapp/workspace/com/vitria/m3oui/ws/environment/view
