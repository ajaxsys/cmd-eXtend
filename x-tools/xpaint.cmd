@echo off
REM Use commonEvents.vbs to control windows applications
SET vbsPath=%~dp0%\..\tools\vbs\

REM open Ms Paint & save it.(In windows7, for a common compress)
start "" mspaint.exe "C:\Documents and Settings\HouTokki\My Documents\My Pictures\1.gif"

REM active window title of [1.gif] and send save key(ctrl+s)
REM ^ == ctrl
%vbsPath%commonEvents.vbs "1.gif" "^s" 100

REM Close(alt+F4)
REM %%->% == alt
%vbsPath%commonEvents.vbs "1.gif" "%%{F4}" 100

REM Shift == +    eg. +{ENTER} = shift+Enter