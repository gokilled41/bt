@echo off

call btenv

call al %btdirfrom% FileUtil.java* np acp rn ##FileUtil-2.java
call al %btdirfrom% Constants.java* np acp rn ##Constants-2.java
call al %btdirfrom% Util.java* np acp rn ##Util-2.java
call al %btdirfrom% Test.java* np acp rn ##Test-2.java

call al bts FileUtil.java* np acp rn ##FileUtil.java
call al bts Constants.java* np acp rn ##Constants.java
call al bts Util.java* np acp rn ##Util.java
call al bts Test.java* np acp rn ##Test.java

if "%btenv%"=="gzhou" call kbat
if "%btenv%"=="chudy" call kbat2
