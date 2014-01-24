@echo off
REM find.vbs not support UTF-16!


REM Supports string input
FOR /F "usebackq delims=" %%i in (`cscript //Nologo find.vbs aaa1245bbb32423 [0-9]+`) DO SET RESULT=%%i
echo %RESULT%

REM Supports file path & RegEx
FOR /F "usebackq delims=" %%i in (`cscript //Nologo find.vbs replace.cmd delims.*`) DO SET RESULT=%%i
echo %RESULT%

REM When no result ,returns "Not found."
FOR /F "usebackq delims=" %%i in (`cscript //Nologo find.vbs replace.cmd notfound`) DO SET RESULT=%%i
echo %RESULT%

REM can use ""
FOR /F "usebackq delims=" %%i in (`cscript //Nologo find.vbs replace.cmd "cscript //Nologo replace"`) DO SET RESULT=%%i
echo %RESULT%

pause