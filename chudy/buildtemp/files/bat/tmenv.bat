@echo off
call set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_60
call set JRE_HOME=%JAVA_HOME%\jre
call set CATALINA_HOME=D:\soft\tomcat\apache-tomcat-8.0.41
call set CATALINA_OPTS=-Xms128m -Xmx512m
call set CATALINA_OPTS=%CATALINA_OPTS% -Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n