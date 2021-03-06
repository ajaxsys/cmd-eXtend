Option Explicit

' NOT support UTF-16!!

'In CMD:
'FOR /F "usebackq delims=" %%i in (`cscript //Nologo "%~dp0\..\tools\vbs\findDesktop.vbs"`) DO SET DESKTOPDIR=%%i
Const NoResult="Not found."
Const USAGE = "Usage: find str condition"
If WScript.Arguments.Count()<2 Then
  WScript.echo USAGE
  WScript.echo NoResult
  WScript.Quit
End If

Dim objRegExp
Set objRegExp = New RegExp
objRegExp.IgnoreCase = True
objRegExp.Global = false

Dim strRepBefore
Dim objMatches
Dim objMatch
Dim strMessage

strRepBefore      = WScript.Arguments.Item(0)
objRegExp.Pattern = WScript.Arguments.Item(1)

Dim fso
Dim reader
Set fso = CreateObject("Scripting.FileSystemObject")

' If file
If (fso.FileExists(strRepBefore)) then
  Set reader = fso.OpenTextFile(strRepBefore, 1)
  strRepBefore = reader.ReadAll
End If

Set objMatches = objRegExp.Execute(strRepBefore)

If (objMatches.Count = 0) then
  WScript.echo NoResult
  WScript.Quit
End If

For Each objMatch In objMatches
    WScript.echo objMatch.Value
Next


Set objRegExp = Nothing