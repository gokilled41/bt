d:
cd D:\jedi\yoda\bw\src\bc
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/m3oui_dms_integration/yoda/bw/src/bc/
cd D:\jedi\yoda\workflow
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/m3oui_dms_integration/yoda/workflow/
cd D:\jedi\yoda\m3o\bpa\emf\server\src
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/m3oui_dms_integration/yoda/m3o/bpa/emf/server/src/
cd D:\jedi\yoda\m3o\j2ee\src
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/m3oui_dms_integration/yoda/m3o/j2ee/src/
cd D:\jedi\yoda\m3o\server\src\core
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/m3oui_dms_integration/yoda/m3o/server/src/core
cd D:\jedi\yoda\m3o\server\src\engine
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/m3oui_dms_integration/yoda/m3o/server/src/engine
cd D:\jedi\yoda\m3o\server\src\workflow
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/m3oui_dms_integration/yoda/m3o/server/src/workflow
cd D:\jedi\yoda\m3o\server\src\sql
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/m3oui_dms_integration/yoda/m3o/server/src/sql
cd D:\jedi\yoda\m3o\ui\src\m3o
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/m3oui_dms_integration/yoda/m3o/ui/src/m3o/
cd D:\jedi\yoda\m3o\ui\src\m3oModule
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/m3oui_dms_integration/yoda/m3o/ui/src/m3oModule/
cd D:\jedi\yoda\m3o\utilities\src\utilityclient
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/m3oui_dms_integration/yoda/m3o/utilities/src/utilityclient/
cd D:\jedi\yoda\bw\src\sql
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/m3oui_dms_integration/yoda/bw/src/sql
cd D:\jedi\yoda\esms\src\m3omodules\querycomponent\src\com\vitria\esms\querycomponent\adaptor\convertor
call svn sw http://vt-sjc-srcsvn.vitria.com/svn/jedi/yoda_branches/m3oui_dms_integration/yoda/esms/src/m3omodules/querycomponent/src/com/vitria/esms/querycomponent/adaptor/convertor
call ant -f C:\workspace\buildtemp\build.xml dmsintegration
d:
