@echo off

if [%1] EQU [] (
	ECHO "Usage: gvc groovysrcfile.groovy"
	GOTO END
)

REM This tool compile groovy file to classes files
call gsh %~dp0\groovy-script\GroovyCompiler.groovy %1

:END