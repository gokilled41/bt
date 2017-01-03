@echo off

if not "%saenv%"=="" goto end

if exist D:\gzhou\sa\OI_1\wildfly set saenv=wildfly
if exist D:\gzhou\sa\OI_1\jboss set saenv=jboss

echo saenv=%saenv%

:end
