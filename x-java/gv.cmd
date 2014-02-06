@echo off
call cmdx-config.cmd

if not exist "%GROOVY_ALL%" (
	ECHO [WARN] Need groovy-all.jar. 
	ECHO Download from http://repo1.maven.org/maven2/org/codehaus/groovy/groovy-all/2.0.0/groovy-all-2.0.0.jar
	pause
	GOTO END
)

set cmdXJar=%~dp0java\cmdx.jar
start "gv" java -cp "%GROOVY_ALL%;%cmdXJar%";. groovy/GroovyConsole

:END