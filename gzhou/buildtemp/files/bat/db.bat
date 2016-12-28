@echo off
call yodadir
cd build\imports
call ant

@echo off
call yodadir
cd build\properties
call ant

@echo off
call yodadir
cd build\dtd
call ant

@echo off
call yodadir
cd build\deploy
call ant

@echo off
call yodadir
cd bw\installer
call ant

@echo off
call yodadir
cd bw\src
call ant

@echo off
call yodadir
cd m3o\server\installer
call ant

@echo off
call yodadir
cd m3o\server\src
call ant

@echo off
call yodadir
cd m3o\server\locale
call ant

@echo off
call yodadir
cd m3o\ui\installer
call ant

@echo off
call yodadir
cd m3o\ui\src
call ant

@echo off
call yodadir
cd m3o\j2ee\installer
call ant

@echo off
call yodadir
cd m3o\j2ee\src
call ant

@echo off
call yodadir
cd m3o\bpa\emf\server\installer
call ant

@echo off
call yodadir
cd m3o\bpa\emf\server\src
call ant

@echo off
call yodadir
cd m3o\projectrole\installer
call ant

@echo off
call yodadir
cd m3o\projectrole\src
call ant

@echo off
call yodadir
cd m3o\utilities\installer
call ant

@echo off
call yodadir
cd m3o\utilities\src
call ant

@echo off
call yodadir
cd m3o\collaboration\installer
call ant

@echo off
call yodadir
cd m3o\collaboration\src
call ant

@echo off
call yodadir
cd m3o\workflow\installer
call ant

@echo off
call yodadir
cd m3o\workflow\src
call ant

@echo off
call yodadir
cd esms\installer
call ant

@echo off
call yodadir
cd esms\src
call ant

@echo off
call yodadir
cd opbook\installer
call ant

@echo off
call yodadir
cd opbook\src
call ant

@echo off
call yodadir
cd shared\preconfigured\wildfly
call ant

@echo off
call yodadir
cd unbundled\af
call ant

@echo off
call yodadir
cd unbundled\apps
call ant

@echo off
call yodadir
cd D:\jedi\branches\wildflybrew\trunk\wildfly-core-1.0.2.Final-src\controller\src\main
call ant

