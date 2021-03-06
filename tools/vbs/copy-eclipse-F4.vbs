'*********************************************************
'Copy class info from eclipse class hierarchy info( F4 )
'*********************************************************

Set objWshShell = WScript.CreateObject("WScript.Shell")

For intCounter = 1 To 500
	ActivateAndSendKeys "Eclipse","%E",200
	ActivateAndSendKeys "Eclipse","Y",10
	ActivateAndSendKeys "Eclipse","{DOWN}",100

	ActivateAndSendKeys "メモ帳","^V",200
	ActivateAndSendKeys "メモ帳","{END}",20
	ActivateAndSendKeys "メモ帳","{ENTER}",20
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

commonEvents=0