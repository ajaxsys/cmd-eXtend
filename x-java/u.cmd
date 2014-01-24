@ECHO off
REM get unicode
set tmpfile=%TMP%\xlsldj45rusdy644haselrjsfkltafil23lasxx

REM check tools, use [which] cmd
which native2ascii.exe | findstr native2ascii > %tmpfile%
IF %ERRORLEVEL%==1 (
  ECHO ========================================
  ECHO native2ascii.exe not exist. Please install JDK, and set it to path
  ECHO ========================================
  GOTO END
)

REM Check if is an existed file(Not support folder or multi files)
SET isFile=yes
IF NOT EXIST %1 (
	GOTO STRING_MODE
)
SET A=%~a1
IF %A:~0,1%==d (
	GOTO STRING_MODE
)

:FILE_MODE
call native2ascii.exe %1
GOTO END


:STRING_MODE

echo %* > %tmpfile%

native2ascii.exe %tmpfile% %tmpfile%_out
echo INPUT:
type %tmpfile%
echo OUTPUT:
type %tmpfile%_out

REM clear
del %tmpfile%_out

:END
del %tmpfile%