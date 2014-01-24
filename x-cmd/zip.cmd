@echo off
REM Use jar.exe to zip
REM jar cvfM abc.zip tt1 tt2 res.txt

IF [%1] EQU [] (
  ECHO Usage: zip zipFile.zip file1 folder1 file2 folder2 ...
  GOTO END
)

set toolpath=%~dp0..\tools\vbs\

REM List the zip if param.length = 1
IF [%2] EQU [] (
  cscript //Nologo "%toolpath%ziplist.vbs" %1
  GOTO END
)

REM Zip process
IF EXIST %1 (
  ECHO Zip [%1] file already existed.
  GOTO END
)

cscript //Nologo "%toolpath%zip.vbs" %*

:END