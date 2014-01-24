@echo off
call cmdx-config.cmd

if not exist "%GROOVY_ALL%" (
	ECHO [WARN] Need groovy-all.jar. 
	ECHO Download from http://repo1.maven.org/maven2/org/codehaus/groovy/groovy-all/2.0.0/groovy-all-2.0.0.jar
	pause
	GOTO END
)

if [] EQU [%1] (
	ECHO Please input a groovy file name.
	GOTO END
)

if not exist %1 (
	ECHO Groovy file "%1" is not existed.
	GOTO END
)

set GV_PATH=%~dp0;%~dp0groovy-script\
java -cp "%GROOVY_ALL%;%GV_PATH%;" groovy/Groovysh %*

REM POM.XML
REM 		<dependency>
REM 			<groupId>org.codehaus.groovy</groupId>
REM 			<artifactId>groovy-all</artifactId>
REM 			<version>2.0.0</version>
REM 		</dependency>
REM 

:END
exit /b %ERRORLEVEL%
