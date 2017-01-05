call saenv
call sa1dir
cd bin
if "%saenv%"=="jboss" call vtportutil -f ..\..\ports\ports.1099.txt
if "%saenv%"=="wildfly" call vtportutil -f ..\..\ports\ju.ports.1099.txt