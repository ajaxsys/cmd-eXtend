@echo off
SET USAGE=USAGE: download http://www.google.co.jp/images/srpr/logo3w.png output.gif
if [%2]==[] (
	ECHO %USAGE%
	GOTO END
)
REM Use pure java version to download groovy-all itself
REM call gsh %~dp0\groovy-script\download.groovy %*

call cmdx-config.cmd

set GV_PATH=%~dp0groovy-script\;%~dp0groovy-script\groovy-script-classes\download.groovy.jar;
REM Use gvc.cmd to create classes files before.
java -cp "%GROOVY_ALL%;%GV_PATH%;" download %*

:END