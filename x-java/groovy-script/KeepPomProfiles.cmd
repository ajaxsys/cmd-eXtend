@echo off

if [%2] EQU [] (
	ECHO "Usage: XMLNodeRemove file.xml nodeXpath"
	GOTO END
)

call gsh %~dp0\KeepPomProfiles.groovy %*

:END