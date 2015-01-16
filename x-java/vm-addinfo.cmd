@echo off

REM	if [%1] EQU [] (
REM		ECHO "Usage: vmformat [clear]"
REM		GOTO END
REM	)

REM This tool merge js/css files to a single html file
call gsh %~dp0\groovy-script\VM-AddInfo.groovy .\ %*

:END