@ECHO OFF
IF [%1]==[] (
  ECHO Usage : rm file1 file2 folder1 ...
  GOTO End
)

:Loop

IF [%1]==[] (
  GOTO End
)

SET A=%~a1

IF NOT EXIST %1 (
	ECHO         [WARN] File [%1] not exist.
	GOTO Continue
)

IF %A:~0,1%==d (
	rmdir /S /Q %1
	if not exist %1 ECHO         [INFO] Folder [%1] deleted.
) ELSE (
	del %1
	if not exist %1 ECHO         [INFO] File [%1] deleted.
)


:Continue
SHIFT
GOTO Loop
:End