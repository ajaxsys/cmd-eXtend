@ECHO off

if "" EQU "%1" (
	ECHO [WARN] Usage : "man CommandName"
	GOTO END
)

REM Get result of "which %1" command
for /f "usebackq tokens=*" %%i in (`which %1`) do @set RESULT=%%i

IF NOT EXIST "%RESULT%" (
	ECHO Command "%1" is not existed.
	GOTO END
)

set HELP_FILE=%~dp0doc\%1.txt

IF NOT EXIST "%HELP_FILE%" (
	REM ECHO Help of command "%1" is not existed.
	REM Set result of findstr to var
	for /f "usebackq tokens=*" %%i in (`type "%~dp0readme.txt" ^| findstr %1.cmd `) do @set TT=%%i
        REM Replace space to empty
	echo %TT: =%
	GOTO END
) 

type "%HELP_FILE%"


:END