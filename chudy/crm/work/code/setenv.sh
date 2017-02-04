#!/bin/sh

export CATALINA_BASE=/fmsdata/apache-tomcat-7.0.67
export CATALINA_HOME=/fmsdata/apache-tomcat-7.0.67
export CATALINA_TMPDIR=/fmsdata/apache-tomcat-7.0.67/temp
export JAVA_HOME=/fmsdata/jdk1.7.0_79
export JAVA_OPTS='-Xms128m -Xmx256m  -XX:PermSize=32m -XX:MaxPermSize=64m -server'
