Option Explicit
Const USAGE="USAGE: zip zipFile.zip"

Dim zipFile
Dim fso
Dim shell
Dim zFolder
Dim rows
Dim k
Dim columns(8)
Dim folderItem

' Validation
If NOT WScript.Arguments.Count()=1 Then
  WScript.Echo USAGE
  WScript.Quit
End If

zipFile = WScript.Arguments.Item(0)

Set fso=CreateObject("Scripting.FileSystemObject")
If Not fso.FileExists(zipFile) Then
  WScript.Echo "ZIP file not found. -",zipFile
  WScript.Quit
End If

Set shell=CreateObject("Shell.Application")
Set zFolder=shell.NameSpace(fso.GetAbsolutePathName(zipFile))
' Get zFolder header details
For k=0 To UBound(columns)
  columns(k)=zFolder.GetDetailsOf(,k)
Next
rows=Array(zipFile)

pushList rows,Join(columns,vbTab)
For Each folderItem In zFolder.Items()
  For k=0 To UBound(columns)
    If k=5 Then
      columns(k)=folderItem.Size
    Else
      columns(k)=zFolder.GetDetailsOf(folderItem,k)
    End If
  Next
  pushList rows,Join(columns,vbTab)
Next
WScript.Echo Join(rows,vbLf)

WScript.Quit

Sub pushList(list,item)
  ReDim Preserve list(UBound(list)+1)
  list(UBound(list))=item
End Sub
