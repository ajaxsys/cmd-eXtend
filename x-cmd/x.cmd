@echo off
call cmdx-config.cmd

REM validation (1)
if "%1"=="" (
	echo Usage: x abc [c:\]
	goto END
)

set FOLDER=.
if not [%2]==[] set FOLDER=%2

REM validation (2) Use [\.] to check a folder.
if not exist %FOLDER%\. (
	echo Folder not exist.Usage: x abc c:\
	goto END
)




REM get path of x.cmd , and create a tmp folder for it
set workDir=%TMP%\x-tmp-file

REM make x.cmd folder if not exits
if not exist "%workDir%" mkdir "%workDir%"
REM clear the result
del /Q "%workDir%\*"


REM find xdoc list 
set outFile=%workDir%\filelist.txt

pushd %FOLDER%
dir /B /S *.doc*,*.pdf,*.xls*,*.ppt* > %outFile%
popd

REM call xdoc2txt
set transFile="%workDir%\fileTransed.txt"
set xdocTools="%~dp0%\..\tools\xdoc2txt.exe"

type nul > %transFile%
FOR /F "tokens=*" %%i IN (%outFile%) DO (
	REM 1:1 create txt file
	REM xdoc2txt.exe -f "%%i" 
	echo ####File: %%i  >> %transFile%
	echo; >> %transFile%
	call %xdocTools% "%%i" >> %transFile%
)






echo Editor: %DEFAULT_EDITOR% | find "Hidemaru"

IF %ERRORLEVEL%==0 (
	REM cls
	REM open and search. CMD escape: | --> ^|
	echo Press [ F3 ] to search
	echo Use the regex search whith key [^####.*^|%1] to view file path.

	REM Hidemaru.exe /gcwrUo,"c:\Folder\*.txt",TEXT_TO_SEARCH
	start "ttl" "%DEFAULT_EDITOR%" /Sr,"%1" %transFile%
	GOTO END
)

echo Editor: %DEFAULT_EDITOR% | find "sakura"
IF %ERRORLEVEL%==0 (
	REM cls
	echo Press [ F12 ] to view detail. 
	echo Use the regex search whith key [^####.*^|%1] to view file path.

	REM sakura.exe -GREPMODE -GFILE="FILES_TO_GREP" -GKEY="Key_of_Grep" -GOPT=P(Output line)
	start "ttl" "%DEFAULT_EDITOR%" -GREPMODE -GFOLDER="%workDir%" -GFILE="fileTransed.txt" -GKEY="%1" -GOPT=P
	GOTO END
)

IF %ERRORLEVEL%==1 (
    REM cls
	echo Press Ctrl+F to search "%1".
	echo To enable the regex search, please use the SAKURA editor or HIDEMARU editor.
	start "ttl" "%DEFAULT_EDITOR%" %transFile%
	goto END
)

:END
