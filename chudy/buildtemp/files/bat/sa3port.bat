call saenv
call sa3dir
cd bin
if "%saenv%"=="jboss" call vtportutil -f ..\..\ports\ports.21099.txt
if "%saenv%"=="wildfly" call vtportutil -f ..\..\ports\ju.ports.21099.txt