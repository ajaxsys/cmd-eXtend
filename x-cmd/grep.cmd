@echo off
REM /S Search sub folder
REM /I No case insense

REM get_parameters
set grep_opt=
set count=0

:_Loop_
IF [%1] EQU [] GOTO _Continue_
	IF [%1] EQU [/S] (
		set grep_opt=%grep_opt% %1
		GOTO next
	)
	IF [%1] EQU [/I] (
		set grep_opt=%grep_opt% %1
		GOTO next
	)
	if %count% EQU 0 (
		SET condition=%1
		set count=1
		GOTO next
	)
	if %count% EQU 1 (
		SET grepPath=%1
		set count=2
		GOTO next
	)
:next
SHIFT
GOTO _Loop_
:_Continue_





REM execute_grep
set USAGE=Usage: grep abc *

if [%condition%] equ [] (
  ECHO %USAGE%
  GOTO END
)

if [%grepPath%] equ [] (
  SET grepPath=*
)

findstr %grep_opt% /R /N /C:"%condition%" %grepPath%

:END