@echo off

if [%1] EQU [] (
	ECHO "Usage: ToText foo.docx foo.txt"
	GOTO END
)

set GV_PATH=%~dp0java\target\dependency\tika-app-1.6.jar;
call c:\jdk1.8.0-b109\bin\java.exe -jar tika-app-1.6.jar -t %1 > %2

:END