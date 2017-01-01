@echo off
set INTERPRETER_DIR=%1

if %INTERPRETER_DIR%=="" echo "abc"

  set INTERPRETER_DIR_1=%INTERPRETER_DIR%
  echo %INTERPRETER_DIR_1%
  echo "%INTERPRETER_DIR:~1,-1%"
  echo %INTERPRETER_DIR%
  
  echo "%INTERPRETER_DIR%"
  echo %INTERPRETER_DIR:~1,-1%
  echo 1
  set abc=D:\gzhou\oi\ab\spark\service\local-repo\Shell_1_Libs\*;D:\gzhou\oi\ab\spark\service\conf\DataPipe\1;
  echo %abc%
  echo 1.5
  if "%INTERPRETER_DIR:~1,-1%"==%INTERPRETER_DIR% set INTERPRETER_DIR_1=%INTERPRETER_DIR:~1,-1%
  echo 2
  echo "INTERPRETER_DIR: %INTERPRETER_DIR_1%"