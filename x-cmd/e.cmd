@ECHO OFF
REM Method 1
Set targetDir=%1
if [%1] == [] (
  Set targetDir=.
)

explorer %targetDir%

:END





REM REM Method 1
REM	if "%1" == "OK" (
REM	  explorer %~dp1%2
REM	  GOTO END
REM	)
REM
REM	e OK %1
REM
REM	:END





REM REM Another way:
REM	cd > tmpFile
REM	set /p myvar= < tmpFile
REM	del tmpFile 
REM
REM	explorer %myvar%
