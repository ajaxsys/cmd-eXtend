@echo off
call cmdx-config.cmd
if not exist "%DEFAULT_EDITOR%" set DEFAULT_EDITOR=notepad.exe
start "editor" "%DEFAULT_EDITOR%" %1 %2 %3 %4 %5 %6 %7 %8 %9