@echo off

call btenv

call al %btdirfrom% FileUtil.java* np acp rn ##{n}-2.java
call al %btdirfrom% Constants.java* np acp rn ##{n}-2.java
call al %btdirfrom% Util.java* np acp rn ##{n}-2.java
call al %btdirfrom% Test.java* np acp rn ##{n}-2.java
call al %btdirfrom% PAUnit.java* np acp rn ##{n}-2.java

call al %btdirfrom% .java\Criterion np acp rn ##{n}-2.java

call al bts FileUtil.java* np acp rn
call al bts Constants.java* np acp rn
call al bts Util.java* np acp rn
call al bts Test.java* np acp rn
call al bts PAUnit.java* np acp rn

call al bt .java\Criterion np acp rn

if "%btenv%"=="gzhou" call kbat
if "%btenv%"=="chudy" call kbat2

call al rn -2 ds
