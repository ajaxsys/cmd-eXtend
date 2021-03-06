'*********************************************************
'用途: ExcelのFilterへCopy&Paste。
'使い方：１）先に隣のシートにPaste内容用意
'        ２）Copy&Paste先にFocusして置く必要。
'        ３）Copy&Paste先シートを選択し、実行！
'        ４）Copy&Paste行数合わせて、下記の数字修正し、実行！
PASTE_NUM=22
'*********************************************************

TITLE_MAIN = "Microsoft Excel"

Set objWshShell = WScript.CreateObject("WScript.Shell")

For i = 0 To PASTE_NUM
    CopyACell()
Next

Function CopyACell()
    ActivateAndSendKeys TITLE_MAIN,"","^c",100
    ActivateAndSendKeys TITLE_MAIN,"","{DOWN}",100
    ActivateAndSendKeys TITLE_MAIN,"","^{PGUP}",100
    ActivateAndSendKeys TITLE_MAIN,"","^v",100
    ActivateAndSendKeys TITLE_MAIN,"","{DOWN}",100
    ActivateAndSendKeys TITLE_MAIN,"","^{PGDN}",100
End Function


'*********************************************************
'用途: 指定したタイトルのウィンドウをアクティブにし、指定
'      したキー・コードを送り、数ミリ秒待つ。
'受け取る値: strTitle: ウィンドウ・タイトル（String）
'              strKey: 送るキー・コード（String）
'             intWait: キー・コードを送った後待つミリ秒数
'                      （Integer）
'戻り値: 失敗したら、終了。
'*********************************************************
Function ActivateAndSendKeys(strTitle, strKeyFocus, strKeyInput, intWait)
    Dim intCounter
    Dim ret
    ret = False
    '10回試行する。
    For intCounter = 1 To 10
        'AppActivateメソッドを実行し、戻り値がTrueなら、
        If objWshShell.AppActivate(strTitle) Then
            
            'キー・コードを送る。
            If strKeyFocus<>"" Then
                WScript.Sleep 100
                objWshShell.SendKeys strKeyFocus
            End If
            'キー・コードを送る。
            WScript.Sleep 100
            objWshShell.SendKeys strKeyInput

            'intWaitミリ秒待つ。
            WScript.Sleep intWait
            '成功を意味するTrueを返し、ループを抜ける。
            ret = True
            Exit For
        Else
            WScript.Sleep 500
            '失敗を意味するFalseを返し、続行。
        End If
    Next

    IF ret = False Then
         MsgBox "指定したタイトルのウィンドウをアクティブにエラー発生！"
         WScript.Quit
    End If
    
End Function

