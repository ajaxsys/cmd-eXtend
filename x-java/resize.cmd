@echo off
SET USAGE_FILE=resize file_in file_out width[int,px]
SET USAGE_FOLDER=resize folder[\] width[int,px]     *with [\] , ouput to self-folder
if [%2]==[] (
	ECHO To resize single image : %USAGE_FILE%
	ECHO To resize folder       : %USAGE_FOLDER%
	GOTO END
)

SET A=%~a1
IF "%A:~0,1%" EQU "d" (
	GOTO DIR
) ELSE (
	call gsh %~dp0\groovy-script\resize.groovy %1 %2 %3
	GOTO END
)

:DIR
SET inDIR=%1
SET outDIR=%1_%2
if EXIST %outDIR% (
	ECHO Directory [%outDIR%] is already existed. Delete?[Y/N]
	SET /P choice=
	SET result=%choice:y=Y%
	if "%result%"=="Y" (
		rmdir /Q /S %outDIR%
	) else (
		echo Nothing happened.
		GOTO END
	)
)
mkdir %outDIR%

SET outFile=%TMP%\xlw492hsy893hks9y23k4x934kx3433489x4783sr.txt

pushd %inDIR%
dir /B *.jpg*,*.jpeg,*.png,*.bmp,*.gif > %outFile%
popd

REM Shui tai shen le:1. EnableDelayedExpansion 2. !errorlevel!
SetLocal EnableDelayedExpansion
FOR /F "tokens=*" %%i IN (%outFile%) DO (
	
	call gsh %~dp0\groovy-script\resize.groovy %inDIR%\%%i %outDIR%\%%i %2
	REM NOTICE: %errorlevel% will be set before for

	if !errorlevel! EQU 9 (
		echo System ERROR occured, Please check parameters. errorlevel=!errorlevel!
		goto END
	)

	if !errorlevel! EQU 1 (
		echo Resize NG : %outDIR%\%%i
	) else (
		echo Resize OK : %outDIR%\%%i
	)
)

del %outFile%

:END