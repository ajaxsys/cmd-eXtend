Option Explicit

'In CMD:
'FOR /F "usebackq delims=" %%i in (`cscript //Nologo "%~dp0\..\tools\vbs\findDesktop.vbs"`) DO SET DESKTOPDIR=%%i

Dim USAGE
USAGE = "Usage: replace str from to"
If WScript.Arguments.Count()<3 Then
  WScript.echo USAGE
  WScript.Quit
End If

Dim objRegExp
Set objRegExp = New RegExp
objRegExp.IgnoreCase = False
objRegExp.Global = True

Dim strRepBefore
Dim strRepAfter

strRepBefore      = WScript.Arguments.Item(0)
objRegExp.Pattern = WScript.Arguments.Item(1)
strRepAfter       = WScript.Arguments.Item(2)

strRepAfter = objRegExp.Replace(strRepBefore, strRepAfter)

WScript.Echo strRepAfter

Set objRegExp = Nothing