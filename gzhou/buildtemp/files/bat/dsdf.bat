@echo off
call yodadir
call svn diff build\imports build\properties build\dtd build\deploy build\lib build\jbison bw\installer bw\src bw\locale bw\devtests m3o\server\installer m3o\server\src m3o\server\locale m3o\server\qatests m3o\ui\installer m3o\ui\src m3o\ui\projects m3o\j2ee\installer m3o\j2ee\src m3o\bpa\emf\server\installer m3o\bpa\emf\server\src m3o\projectrole\installer m3o\projectrole\src m3o\utilities\installer m3o\utilities\src m3o\utilities\conf m3o\collaboration\installer m3o\collaboration\src m3o\workflow\installer m3o\workflow\src esms\installer esms\src opbook\installer opbook\src shared suites unbundled\af unbundled\apps > "C:\Users\gzhou\Desktop\yoda.patch"
if "%mdf%"=="" call explorer "C:\Users\gzhou\Desktop\yoda.patch"
