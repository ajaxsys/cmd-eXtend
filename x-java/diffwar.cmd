@echo off

if [%1] EQU [] (
	ECHO "Usage: WarDiff a.war b.war"
	GOTO END
)

call gsh %~dp0\groovy-script\WarDiff.groovy %*

:END