@echo off
REM JDK Version. Use jar.exe to unzip
REM jar xvf %1

set zipFile="%~df1"

IF [%1] EQU [] (
  ECHO Usage: unzip file.zip [folder]
  GOTO end
)

IF NOT [%2] EQU [] (
  if exist %2 rmdir /Q /S %2
  mkdir %2
  pushd %2
  ECHO  [INFO] Unzip to folder [%2] :
) else (
  ECHO  [INFO]  Unzip to current folder :
)

jar xvf %zipFile%

IF NOT [%2] EQU [] (
  popd
)
:end








REM	REM VBA Version
REM	
REM	IF [%1] EQU [] (
REM	  ECHO Usage: unzip file.zip [folder]
REM	  GOTO end
REM	)
REM	
REM	set toolpath=%~dp0..\tools\vbs\
REM	
REM	IF [%2] EQU [] (
REM	  ECHO Unzip to current folder.
REM	  GOTO do_unzip
REM	) 
REM	
REM	IF NOT EXIST %2 mkdir %2
REM	ECHO Unzip to folder [%2].
REM	
REM	:do_unzip (WARN: vbs not reginez extends with "war,jar...") 
REM	
REM	call cscript //Nologo "%toolpath%unzip.vbs" %1 %2
REM	
REM	:end
REM	
REM	
REM	
