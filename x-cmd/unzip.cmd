@echo off
REM Use jar.exe to unzip
REM jar xvf %1

IF [%1] EQU [] (
  ECHO Usage: unzip file.zip [folder]
  GOTO end
)

set toolpath=%~dp0..\tools\vbs\

IF [%2] EQU [] (
  ECHO Unzip to current folder.
  GOTO do_unzip
) 

IF NOT EXIST %2 mkdir %2
ECHO Unzip to folder [%2].


:do_unzip
cscript //Nologo "%toolpath%unzip.vbs" %1 %2
:end