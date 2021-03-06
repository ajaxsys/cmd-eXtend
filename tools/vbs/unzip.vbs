Option Explicit
Const USAGE="USAGE: unzip zipFile.zip [folder]"
Dim zipFile
Dim fso
Dim shell
Dim zFolder
Dim dFolder

'Validation
If WScript.Arguments.Count()<1 Then
  WScript.Echo USAGE
  WScript.Quit
End If

zipFile=WScript.Arguments.Item(0)
Set fso=CreateObject("Scripting.FileSystemObject")

If Not fso.FileExists(zipFile) Then
  WScript.Echo "ZIP file not found. -",zipFile
  WScript.Quit
End If

zipFile=fso.GetAbsolutePathName(zipFile)

Set shell=CreateObject("Shell.Application")
Set zFolder=shell.NameSpace(fso.GetAbsolutePathName(zipFile))

If WScript.Arguments.Count<2 Then
  Set dFolder=shell.NameSpace(fso.GetAbsolutePathName(".\\"))
  dFolder.CopyHere zFolder.Items()
Else
  Set dFolder=shell.NameSpace(fso.GetAbsolutePathName(WScript.Arguments.Item(1)))
  If dFolder Is Nothing Then
    WScript.Echo WScript.Arguments.Item(1),"- Not Found."
  Else
    dFolder.CopyHere zFolder.Items()
  End If
End If

WScript.Quit
