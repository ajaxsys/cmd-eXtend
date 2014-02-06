@echo off
call cmdx-config.cmd

if not exist "%CODEC_JAR%" (
	ECHO [WARN] Need commons-codec.jar. 
	ECHO Download from http://repo1.maven.org/maven2/commons-codec/commons-codec/1.5/commons-codec-1.5.jar
	ECHO ^<dependency^>
	ECHO 	^<groupId^>commons-codec^</groupId^>
	ECHO 	^<artifactId^>commons-codec^</artifactId^>
	ECHO 	^<version^>1.5^</version^>
	ECHO ^</dependency^>
	pause
	GOTO END
)


if "%4" EQU "" (
	set option=-e
	set password=%1
	set input=%2
	set output=%3
) else (
	set option=%1
	set password=%2
	set input=%3
	set output=%4
)

REM encript & base64
set USAGE1=Usage: encode password input.bin base64.txt
REM decode base64 & decript
set USAGE2=Usage: encode -d password base64.txt output.bin
REM encript only
set USAGE3=Other: encode -ec password input.bin ouput.bin
REM decript only
set USAGE4=Other: encode -dc password input.bin ouput.bin
REM encode base64 only
set USAGE5=Other: encode -eb password input.bin base64.txt     REM * password no use, but need
REM decode base64 only
set USAGE6=Other: encode -db password base64.txt ouput.bin     REM * password no use, but need

if [%output%] EQU [] (
	echo %USAGE1%
	echo %USAGE2%
	echo %USAGE3%
	echo %USAGE4%
	echo %USAGE5%
	echo %USAGE6%
	goto end
)

set SS_PATH=%~dp0%\java\cmdx.jar
java -classpath "%CODEC_JAR%;%SS_PATH%;" encrypt.SS %option% %password% %input% %output%


REM for %%i in (lib\*.jar) do @call :addjar %%i
REM :addjar
REM set JAR=%JAR%%1;

:END