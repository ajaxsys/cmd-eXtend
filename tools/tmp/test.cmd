if not "%1" == "OK" (
  test OK
  GOTO END
)

echo %%~1      =      %~1 
echo %%~f1     =      %~f1
echo %%~d1     =      %~d1
echo %%~p1     =      %~p1
echo %%~n1     =      %~n1
echo %%~x1     =      %~x1
echo %%~s1     =      %~s1
echo %%~a1     =      %~a1
echo %%~t1     =      %~t1
echo %%~z1     =      %~z1
echo %%~$PATH:1=      %~$PATH:1
echo %%~dp1    =      %~dp1
echo %%~nx1    =      %~nx1
echo %%~dp$PATH:1 =   %~dp$PATH:1
echo %%~ftza1  =      %~ftza1

REM file path
echo %%~f0     =      %~f0
REM file size
echo %%~z0     =      %~z0

:END


for %%* in (.) do set CurrDirName=%%~n*
echo CurrDirName: %CurrDirName%

pause