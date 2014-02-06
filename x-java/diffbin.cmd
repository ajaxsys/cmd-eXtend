@Echo off

Set usage=USAGE: diffbin a.txt b.txt 
Set why1=File not exist.

if [%2]==[] (
	Echo %usage%
	GOTO End
)

Set file1=%~dpf1
Set file2=%~dpf2

if not exist %file1% (
	Echo %why1% %usage%
	GOTO End
)

if not exist %file2% (
	Echo %why1% %usage%
	GOTO End
)

REM Size different must be different binary
FOR %%? IN (%file1%) DO ( SET /A filelength1=%%~z? - 2 )
FOR %%? IN (%file2%) DO ( SET /A filelength2=%%~z? - 2 )
if not "%filelength1%"=="%filelength2%" (
	Echo File is different.
	GOTO END
)

FOR /F "usebackq delims=" %%i in (`MD5 %file1%`) DO SET md5_result1=%%i
FOR /F "usebackq delims=" %%i in (`MD5 %file2%`) DO SET md5_result2=%%i
if "%md5_result1%"=="%md5_result2%" (
	Echo File is same.
) else (
	Echo File is different.
)

:End