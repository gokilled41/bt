@echo off

call btenv

call al %btdir% FileUtil.java* np acp rn ##FileUtil-2.java
call al %btdir% Constants.java* np acp rn ##Constants-2.java
call al %btdir% Util.java* np acp rn ##Util-2.java
call al %btdir% Test.java* np acp rn ##Test-2.java

call al bts FileUtil.java* np acp rn ##FileUtil.java
call al bts Constants.java* np acp rn ##Constants.java
call al bts Util.java* np acp rn ##Util.java
call al bts Test.java* np acp rn ##Test.java

if "%btenv%"=="gzhou" call kbat
if "%btenv%"=="chudy" call kbat2
