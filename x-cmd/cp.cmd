@ECHO off
SET USAGE=Usage: cp fileOrFolder fileOrFolder2
IF [] EQU [%1] (
	ECHO %USAGE%
	GOTO End
)
IF [] EQU [%2] (
	ECHO %USAGE%
	GOTO End
)

IF NOT EXIST %1 (
	ECHO         [WARN] File [%1] not exist.
	GOTO End
)

SET A=%~a1
IF %A:~0,1%==d (
	xcopy /Q /I /E %1 %2
	echo [INFO] Folder [%2] list:
	dir /B %2
) ELSE (
	copy /B %1 %2
)

:End