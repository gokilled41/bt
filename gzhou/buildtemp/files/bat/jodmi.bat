set JOD3=D:\soft\aliyun\jodconverter\jodconverter-core-3.0-beta-4
call mvn install:install-file -DgroupId=localjod3 -DartifactId=jodconverter -Dversion=3.0.4 -Dpackaging=jar -Dfile=%JOD3%\lib\jodconverter-core-3.0-beta-4.jar
call mvn install:install-file -DgroupId=localjod3 -DartifactId=commons-cli -Dversion=1.1 -Dpackaging=jar -Dfile=%JOD3%\lib\commons-cli-1.1.jar
call mvn install:install-file -DgroupId=localjod3 -DartifactId=commons-io -Dversion=1.4 -Dpackaging=jar -Dfile=%JOD3%\lib\commons-io-1.4.jar
call mvn install:install-file -DgroupId=localjod3 -DartifactId=json -Dversion=1.0 -Dpackaging=jar -Dfile=%JOD3%\lib\json-20090211.jar
call mvn install:install-file -DgroupId=localjod3 -DartifactId=juh -Dversion=3.2.1 -Dpackaging=jar -Dfile=%JOD3%\lib\juh-3.2.1.jar
call mvn install:install-file -DgroupId=localjod3 -DartifactId=jurt -Dversion=3.2.1 -Dpackaging=jar -Dfile=%JOD3%\lib\jurt-3.2.1.jar
call mvn install:install-file -DgroupId=localjod3 -DartifactId=ridl -Dversion=3.2.1 -Dpackaging=jar -Dfile=%JOD3%\lib\ridl-3.2.1.jar
call mvn install:install-file -DgroupId=localjod3 -DartifactId=unoil -Dversion=3.2.1 -Dpackaging=jar -Dfile=%JOD3%\lib\unoil-3.2.1.jar
