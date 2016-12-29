@echo off
call yodadir
cd unbundled\apps\spark_pipe
call ant

@echo off
call yodadir
cd unbundled\thirdparty\zeppelin\v0.6.1\dist
call ant

@echo off
call yodadir
cd unbundled\thirdparty\zeppelin\v0.6.1\bin
call ant

@echo off
call yodadir
cd unbundled\thirdparty\zeppelin\v0.6.1\spark
call ant

@echo off
call yodadir
cd unbundled\thirdparty\zeppelin\v0.6.1\zeppelin-interpreter
call ant

@echo off
call yodadir
cd unbundled\thirdparty\zeppelin\v0.6.1\zeppelin-server
call ant

@echo off
call yodadir
cd unbundled\thirdparty\zeppelin\v0.6.1\zeppelin-vitria
call ant

@echo off
call yodadir
cd unbundled\thirdparty\zeppelin\v0.6.1\zeppelin-zengine
call ant

@echo off
call yodadir
cd unbundled\spark
call ant

@echo off
call yodadir
cd unbundled\eventstore
call ant

