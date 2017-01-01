d:

cd D:\jedi\yoda\bw\src
call svn diff > d:\bw.src.patch

cd D:\jedi\yoda\m3o\server\src
call svn diff > d:\m3o.server.src.patch

cd D:\jedi\yoda\m3o\workflow
call svn diff > d:\m3o.workflow.patch

rem cd D:\jedi\yoda\m3o\server\locale
rem call svn diff > d:\m3o.server.locale.patch

cd D:\jedi\yoda\m3o\ui\src
call svn diff > d:\m3o.ui.src.patch

cd D:\jedi\yoda\m3o\bpa\emf\server\src
call svn diff > d:\m3o.bpa.emf.server.src.patch

rem cd D:\jedi\yoda\m3o\bpa\emf\server\locale
rem call svn diff > d:\m3o.bpa.emf.server.locale.patch

cd D:\jedi\yoda\esms\src\m3omodules\querycomponent
call svn diff > d:\esms.src.m3omodules.querycomponent.patch

rem cd D:\jedi\yoda\esms\locale
rem call svn diff > d:\esms.locale.patch

cd D:\jedi\yoda\m3o\utilities\src
call svn diff > d:\m3o.utilities.src.patch
