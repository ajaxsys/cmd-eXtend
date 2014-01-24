@echo off
call cmdx-config.cmd

if not exist "%GROOVY_ALL%" (
	ECHO [WARN] Need groovy-all.jar. 
	ECHO Download from http://repo1.maven.org/maven2/org/codehaus/groovy/groovy-all/2.0.0/groovy-all-2.0.0.jar
	pause
	GOTO END
)

pushd %~f0\..\

start "gv" java -cp "%GROOVY_ALL%";. groovy/GroovyConsole


REM POM.XML:
REM 		<dependency>
REM 			<groupId>org.codehaus.groovy</groupId>
REM 			<artifactId>groovy-all</artifactId>
REM 			<version>2.0.0</version>
REM 		</dependency>
REM 

:END