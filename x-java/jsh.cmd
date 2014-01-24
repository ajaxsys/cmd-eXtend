@echo off
set JAVA_FILE=%1

if [] EQU [%JAVA_FILE%] (
	ECHO Usage: jsh test.java
	GOTO END
)
if NOT EXIST %JAVA_FILE% (
	ECHO File %JAVA_FILE% not existed
	GOTO END
)

REM Get Package of java file
SET FIND_CONDITION="^^ *package.*;"
FOR /F "usebackq delims=" %%i in (`cscript //Nologo "%~dp0\..\tools\vbs\find.vbs" %JAVA_FILE% %FIND_CONDITION%`) DO SET PACKAGE=%%i
if "%PACKAGE%" EQU "Not found." SET PACKAGE=
SET JAVA_CLASS=%PACKAGE%%JAVA_FILE%

REM Get Package folder
FOR /F "usebackq delims=" %%i in (`cscript //Nologo "%~dp0\..\tools\vbs\replace.vbs" "%PACKAGE%" "package|;| " ""`) DO SET PACKAGE=%%i
FOR /F "usebackq delims=" %%i in (`cscript //Nologo "%~dp0\..\tools\vbs\replace.vbs" "%PACKAGE%" "\." "\"`) DO SET PACKAGE=%%i
REM ECHO %PACKAGE%

REM Change package&java name to class name
FOR /F "usebackq delims=" %%i in (`cscript //Nologo "%~dp0\..\tools\vbs\replace.vbs" "%JAVA_CLASS%" "package|.java| " ""`) DO SET JAVA_CLASS=%%i
FOR /F "usebackq delims=" %%i in (`cscript //Nologo "%~dp0\..\tools\vbs\replace.vbs" "%JAVA_CLASS%" ";" "."`) DO SET JAVA_CLASS=%%i

REM ECHO %JAVA_CLASS%

SET tmpdir=%TMP%\ltkmk349sfy12hksshkdrh234jlzsf7234ljs0d4
if not exist "%tmpdir%\%PACKAGE%" mkdir "%tmpdir%\%PACKAGE%"

REM Read params from %2. Do NOT use %*, cause it wont change with SHIFT
SHIFT
set PARAMS=
:_Loop_
IF [%1] EQU [] GOTO _Continue_
	SET PARAMS=%PARAMS% %1
SHIFT
GOTO _Loop_
:_Continue_

REM complie & execute java file
javac -d %tmpdir%\ %JAVA_FILE%
java -classpath "%tmpdir%;" %JAVA_CLASS% %PARAMS%

REM clear
rmdir /S /Q %tmpdir%

:END