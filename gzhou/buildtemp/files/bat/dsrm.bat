@echo off
call yodadir
call rmdir /s/q shared\preconfigured\wildfly\modules\system\layers\base\org\jboss\as\deployment-repository\main\fix-reason.txt
call rmdir /s/q shared\preconfigured\wildfly\modules\system\layers\base\org\jboss\as\deployment-repository\main\wildfly-deployment-repository-1.0.2.Final-fixed.jar
