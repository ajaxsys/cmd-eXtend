Option Explicit
Const Usage="Usage: zip zipFile files/folders..."
Dim fso
Dim zipFile
Dim shell
Dim k
Dim path
Dim fileName
Dim zFolder
Dim count
Dim zFolderItem
Dim targetFile

Set fso=CreateObject("Scripting.FileSystemObject")

' Parameter Validation
If WScript.Arguments.Count()<2 Then
  WScript.echo Usage
  WScript.Quit
End If

zipFile=WScript.Arguments.Item(0)
If LCase(fso.GetExtensionName(zipFile))<>"zip" Then
  zipFile=zipFile & ".zip"
End If

If fso.FileExists(zipFile) Then
  WScript.echo zipFile & "is existed."
  WScript.Quit
End If

For k=1 To WScript.Arguments.Count()-1
  path=WScript.Arguments.Item(k)

  If Not ( fso.FileExists(path) OR fso.FolderExists(path) ) Then
    WScript.echo "[WARN] " & path & "- Not Found."
    WScript.Quit
  End If
Next

' Create a empty zip file
fso.CreateTextFile(zipFile,False).Write "PK" & Chr(5) & Chr(6) & String(18,0)

' Process zip
Set shell=CreateObject("Shell.Application")
Set zFolder=shell.NameSpace(fso.GetAbsolutePathName(zipFile))

For k=1 To WScript.Arguments.Count()-1
    path=WScript.Arguments.Item(k)

    fileName=fso.GetFileName(path)
    Set targetFile=shell.NameSpace(fso.GetAbsolutePathName(path)&"\..\").ParseName(fileName)

    ' Check if fileName is existed in zFolder. If existed(eg. duplicated parameter) it will be skipped.
    Set zFolderItem=zFolder.ParseName(fileName)
    If zFolderItem Is Nothing Then
      count=zFolder.Items().Count
      ' Shell.Application.CopyHere Shell.NameSpace
      zFolder.CopyHere targetFile
      ' Waiting for added
      Do While zFolder.Items().Count=count
        WScript.Sleep 100
      Loop
      WScript.echo "Zipped:" & fileName
    End If

Next
WScript.Quit
