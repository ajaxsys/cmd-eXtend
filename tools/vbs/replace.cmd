REM use ^ to escape , and use $N to get the value
FOR /F "usebackq delims=" %%i in (`cscript //Nologo replace.vbs aa123bb ^(aa[0-9]+^) cc$1`) DO SET RESULT=%%i
echo "%RESULT%"

FOR /F "usebackq delims=" %%i in (`cscript //Nologo replace.vbs aa123bb "aa|bb" cc`) DO SET RESULT=%%i
echo "%RESULT%"

pause