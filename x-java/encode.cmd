@echo off
call cmdx-config.cmd

set need_jar=%M2_REPO%\commons-codec\commons-codec\1.5\commons-codec-1.5.jar
if not exist "%need_jar%" (
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

set SS_PATH=%~dp0%\encode\SS.jar
java -classpath "%need_jar%;%SS_PATH%;" encrypt.SS %option% %password% %input% %output%


REM for %%i in (lib\*.jar) do @call :addjar %%i
REM :addjar
REM set JAR=%JAR%%1;

:END