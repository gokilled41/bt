@echo off
call yodadir
call svn commit unbundled\apps\spark_pipe unbundled\thirdparty\zeppelin\v0.6.1\dist unbundled\thirdparty\zeppelin\v0.6.1\bin unbundled\thirdparty\zeppelin\v0.6.1\spark unbundled\thirdparty\zeppelin\v0.6.1\zeppelin-interpreter unbundled\thirdparty\zeppelin\v0.6.1\zeppelin-vitria unbundled\thirdparty\zeppelin\v0.6.1\zeppelin-zengine unbundled\spark unbundled\eventstore -m %1 
