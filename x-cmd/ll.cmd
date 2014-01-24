@ECHO OFF
if exist "%*" (
	dir "%*"
) else (
	dir %*
)