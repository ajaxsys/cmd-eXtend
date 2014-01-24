@ECHO OFF
call cmdx-config.cmd
start "default browser" "%SEARCH_ENGINE%%1 %2 %3 %4 %5 %6 %7 %8 %9"
