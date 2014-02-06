@ECHO ON

REM findstr  /C:"%path%" "x-cmd"
echo %path% | findstr "x-cmd-not-exist"

REM IF %ERRORLEVEL%==0 echo found
IF %ERRORLEVEL%==1 (
  ECHO ========================================
  ECHO Please set "%~dp0;%~dp0x-cmd;%~dp0x-java;%~dp0x-mvn;%~dp0x-tools;%~dp0x-work;" to your path environment.
  ECHO Reference to "readme.txt"
  ECHO ========================================
  REM This will cause problem when ")" in path, becase it will be the end of "IF %ERRORLEVEL%==1 ("
  REM resolve 1: replace ")" to "^)", "(" to "^(". Eg. FOR /F "usebackq delims=" %%i in (`cscript //Nologo "%~dp0\tools\vbs\replace.vbs" "%path%" "(\(|\))" "^$1"`) DO SET OLD_PATH=%%i
  path=%~dp0;%~dp0x-cmd;%~dp0x-java;%~dp0x-mvn;%~dp0x-tools;%~dp0x-work;%path%
)

pushd %USERPROFILE%
cmd
