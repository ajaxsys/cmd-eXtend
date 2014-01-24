@echo off

if [%1] EQU [] (
	ECHO "Usage: htm21 htmlfile.htm"
	GOTO END
)

REM This tool merge js/css files to a single html file
call gsh %~dp0\groovy-script\HtmlAllInOne.groovy %1

:END