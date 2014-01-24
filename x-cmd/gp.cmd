@ECHO OFF
call cmdx-config.cmd
REM FOR /F "usebackq delims=" %%i in (`cscript //Nologo "%~dp0\..\tools\vbs\GetPath.js"`) DO SET RESULT_FILE=%%i

REM set RESULT_FILE=%~dp0\..\tmp\x-tmp\result.txt
REM call cscript //Nologo "%~dp0\..\tools\vbs\GetPath.js" "%RESULT_FILE%"
REM echo %RESULT_FILE%
REM start "ttl" "%DEFAULT_EDITOR%" "%RESULT_FILE%"

cls
call cscript //Nologo "%~dp0\..\tools\vbs\GetPath.js"

pause