@ECHO OFF
REM find both desktop paths in Japanese & English Operating System
FOR /F "usebackq delims=" %%i in (`cscript //Nologo "%~dp0\..\tools\vbs\findDesktop.vbs"`) DO SET DESKTOPDIR=%%i
pushd %DESKTOPDIR%
cls
cmd
