@ECHO OFF
REM =======find if SEARCH_KEY is already in path=======
set SEARCH_KEY=x-cmd

REM findstr  /C:"%path%" "x-cmd"
echo "%path%" | findstr %SEARCH_KEY%
cls
REM Do not use %ERRORLEVEL%==1 it may NG in some case. eg: tmp/bck/cmdx-ng.cmd
IF %ERRORLEVEL%==0 (
  pushd %USERPROFILE%
  GOTO END
)

REM =======Concat the path start with "x-" =======
SET CMDX_PATH=%~dp0;
for /F %%f in ('dir /b %~dp0x-*') DO call :concat %%f
path=%CMDX_PATH%%path%

ECHO ========================================
ECHO Please set "%CMDX_PATH%" to your path environment.
ECHO Reference to "readme.txt"
ECHO ========================================

:END

cmd
REM When cmd is existed
goto EndOfFile

:concat
set CMDX_PATH=%CMDX_PATH%%~dp0%1;
goto EndOfFile
:EndOfFile
