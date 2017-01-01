d:
cd D:\jedi\yoda\workflow\devtests\common\junitee\WorkflowM3OUnitTest\WorkflowM3OUnitTestAuto
call ant > C:\workspace\buildtemp\src\log\WorkflowM3OUnitTestAuto.log
call ant -f C:\workspace\buildtemp\build.xml > C:\workspace\buildtemp\src\log\cleanbuildAfterWorkflowM3OUnitTestAuto.log

cd D:\jedi\yoda\workflow\devtests\client\junitee\query\projects\QueryLibAuto
call ant > C:\workspace\buildtemp\src\log\QueryLibAuto.log
call ant -f C:\workspace\buildtemp\build.xml > C:\workspace\buildtemp\src\log\cleanbuildAfterQueryLibAuto.log

cd D:\jedi\yoda\workflow\devtests\process\junitee\service\projects\InvokeServiceM3O\auto
call ant > C:\workspace\buildtemp\src\log\InvokeServiceM3O.log
call ant -f C:\workspace\buildtemp\build.xml > C:\workspace\buildtemp\src\log\cleanbuildAfterInvokeServiceM3O.log

cd D:\jedi\yoda\workflow\devtests\common\junitee\WorkflowM3OUnitTest\WorkflowM3OTimerUnitTestAuto
call ant > C:\workspace\buildtemp\src\log\WorkflowM3OTimerUnitTestAuto.log
call ant -f C:\workspace\buildtemp\build.xml > C:\workspace\buildtemp\src\log\cleanbuildAfterWorkflowM3OTimerUnitTestAuto.log

cd D:\jedi\yoda\workflow\devtests\bundles\audit\m3o\junitee
call ant > C:\workspace\buildtemp\src\log\audit.log
call ant -f C:\workspace\buildtemp\build.xml > C:\workspace\buildtemp\src\log\cleanbuildAfterAudit.log
