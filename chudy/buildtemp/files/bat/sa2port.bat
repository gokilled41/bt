call saenv
call sa2dir
cd bin
if "%saenv%"=="jboss" call vtportutil -f ..\..\ports\ports.11099.txt
if "%saenv%"=="wildfly" call vtportutil -f ..\..\ports\ju.ports.11099.txt