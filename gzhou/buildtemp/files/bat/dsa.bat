@echo off
call yodadir
call svn add shared\preconfigured\wildfly\modules\system\layers\base\org\jboss\as\deployment-repository\main\fix-reason.txt
call svn add shared\preconfigured\wildfly\modules\system\layers\base\org\jboss\as\deployment-repository\main\wildfly-deployment-repository-1.0.2.Final-fixed.jar
