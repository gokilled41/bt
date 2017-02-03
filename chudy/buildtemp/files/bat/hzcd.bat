@echo off

rem CLEAN WEBAPPS
echo cleaning webapps
call al tmd arm sl

rem DEPLOY
echo deploying crm.war to webapps
call al ke crm.war acp tm\webapps ov sl
call al ke crm.war acp hz ov sl
