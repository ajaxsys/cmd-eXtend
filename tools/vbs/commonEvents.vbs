Const Usage = "Usage: commonEvents.vbs programTitle key1 sleepMiliSeconds1 key2 sleepMiliSeconds2 ..."
Const Usage2= "                          item:0     1    2                 3    4                 ..."

If WScript.Arguments.Count()<2 Then
  CScript.echo Usage
  CScript.Quit
End If

Set objWshShell = WScript.CreateObject("WScript.Shell")

title = WScript.Arguments.Item(0)

For intCounter = 2 To WScript.Arguments.Count()
	If intCounter Mod 2 = 1 Then 'is timer
		'msgbox  title & "," & WScript.Arguments.Item(intCounter-2) & "," & WScript.Arguments.Item(intCounter-1)
		ActivateAndSendKeys title, WScript.Arguments.Item(intCounter-2), WScript.Arguments.Item(intCounter-1)
	End If
Next



'*********************************************************
'Usage: Active given tile of windows, send keys to it and wait
'
'Params: strTitle: title of window (String)
'              strKeyFocus: send focus key(String)
'              strKey: send input key(String)
'             intWait: wait times after send keys (Integer)
'
'Return: Success/Failed (Boolean)
'*********************************************************
Function ActivateAndSendKeys(strTitle, strKeyInput, intWait)
    Dim intCounter
    ' Try 10 times
    For intCounter = 1 To 10
        'AppActivate to check if windows is started up.
        If objWshShell.AppActivate(strTitle) Then
            WScript.Sleep 100'0.1秒待つ。
            'send keys to input
	REM	msgbox strKeyInput
            objWshShell.SendKeys strKeyInput

            'intWait: milisecond to wait
            WScript.Sleep intWait
            'Exit try 10 times
            ActivateAndSendKeys = True
            Exit For
        Else
            WScript.Sleep 500 '500*10=5秒
            'When failed, wait 500 mili-sec
            ActivateAndSendKeys = False
        End If
    Next
End Function


Set objWshShell=Nothing
