@ECHO OFF
for /f "usebackq tokens=*" %%i in (`which compare`) do @set RESULT=%%i
IF NOT EXIST "%RESULT%" (
	ECHO Command "compare" is not existed.Please install "ImageMagick" first.
	GOTO END
)



SET USAGE=Usage: diffpic folder1 folder2 outputfolder
IF [%3]==[] (
	ECHO %USAGE%
	GOTO END
)

REM Chekc param 1 if is a folder
IF not exist %1 (
	ECHO %1 Not exist.
	goto END
)
IF not exist %2 (
	ECHO %2 Not exist.
	goto END
)





SET A=%~a1
IF %A:~0,1%==d (
	ECHO [INFO]Using Folder Diff
	GOTO FOLDER_MODE
)



REM File mode
compare %*



:FOLDER_MODE
IF exist %3 (
	rmdir /S /Q %3
)
mkdir %3

REM Chekc param 2/3 if is a folder

SET A=%~a2
IF not %A:~0,1%==d (
	ECHO %2 NOT A Folder
	GOTO END
)

SET A=%~a3
IF not %A:~0,1%==d (
	ECHO %3 NOT A Folder
	GOTO END
)




call gsh %~dp0\groovy-script\PicDiff.groovy %*





:END