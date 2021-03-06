'*********************************************************
'用途: 日本語版のWinMergeでフォルダ比較の結果を
'      自動的にレポート取得し、取得結果にリンクを付ける
'注意: ①日本語版のみ対応
'      ②WinMergeの下記の３条件
'            ★１つProcessだけ開いて、
'            ★内部Windowを最大化し
'            ★WinmergeのFolderReport機能禁止する ○WinMerge2.13.*以降の新機能
'        ことを確認
'      ③フォルダ比較済み状態、且つツリー表示禁止
'      ④比較結果列でソート（下記BREAK_WHEN_FIRST_SAME=true場合）
'      ⑤下記のフォルダが上書きされること：
SAVE_FOLDER = "C:\diff-result" '←★自分の環境で調整、漢字禁止！
WAIT_FOR_OPEN_DIFF  = 500      '←★自分のPC性能で調整、ミリ秒。DIFFファイルのサイズより変化
WAIT_FOR_SAVE_DIFF  = 500
WAIT_FOR_CLOSE_DIFF = 500
BREAK_WHEN_FIRST_SAME=true    '←★一件目の同一で終了するか（事前にソートされる場合、効率よい）
'*********************************************************


SAVE_INDEX_PATH  = SAVE_FOLDER+"\"+"index.html"
TITLE_MAIN = "WinMerge -"
TITLE_OK = "WinMerge"
TITLE_SAVE_IDX = "フォルダ比較レポート"
TITLE_SAVE = "名前を付けて保存"

'出力フォルダ削除
Set fso = CreateObject("Scripting.FileSystemObject")
'fso.FolderExists(SAVE_FOLDER)
If (fso.FolderExists(SAVE_FOLDER)) then
    fso.DeleteFolder(SAVE_FOLDER)
End If

'WMIにて使用する各種オブジェクトを定義・生成する。
Set objWshShell = WScript.CreateObject("WScript.Shell")

'フォルダ比較結果出力
FolderDiffReport(SAVE_INDEX_PATH)

'フォルダ比較結果分析し、リンク付け
'一括入力
Set reader = fso.OpenTextFile(SAVE_INDEX_PATH, 1)
s = reader.ReadAll
'Do Until reader.AtEndOfStream
'    WScript.Echo reader.ReadLine
'Loop
reader.Close()

Set re = New RegExp
re.IgnoreCase = True
re.Global = True
'Diff行をマッチする.Eg. <tr><td>Search$1.class.java</td>
re.Pattern = "<tr><td>([^<].*?)</td>(.*)"

'数取得
Set matches = re.Execute(s)
DIFF_NUM = matches.Count

'Indexリンク付きでReplace(1件のみReplace)、子ファイルのレポート取得
re.Global = False
For i = 0 To DIFF_NUM-1
	IF InStr(matches(i),"同一") <> 0 THEN
		'DO NOTHING Set white background to avoid replace miss
		s = re.Replace(s, "<tr style='background-color:#ffffff;'><td>$1</td>$2")
		IF BREAK_WHEN_FIRST_SAME THEN
			Exit For
		END If
	ELSEIf InStr(matches(i),"のみ") <> 0 Then
		'左のみ・右のみの場合、Gray、リンク無し
		s = re.Replace(s, "<tr style='background-color:#C0C0C0;'><td>$1</td>$2")
	ELSEIF InStr(matches(i),"バイナリファイルは異なっています") <> 0 OR InStr(matches(i),"<td>jar</td></tr>") <> 0 OR InStr(matches(i),"<td>war</td></tr>") <> 0 OR InStr(matches(i),"<td>zip</td></tr>") <> 0 OR InStr(matches(i),"<td>class</td></tr>") <> 0  OR InStr(matches(i),"<td>exe</td></tr>") <> 0 Then
		'バイナリが異なり、深黄色、リンク無し
		s = re.Replace(s, "<tr style='background-color:#EB5505;'><td>$1</td>$2")
	ELSE
		'テキスト異なり、黄色、リンク有り
		s = re.Replace(s, "<tr style='background-color:#EFCB05;'><td><a target='_blank' href=_diff_"& (i+1) &".html>$1</a></td>$2")
		FileDiffReport(SAVE_FOLDER & "\_diff_" & (i+1) & ".html")
	End If
	GoNext() ' Move nexe line
Next

'一括出力
Set writer = fso.CreateTextFile(SAVE_INDEX_PATH, True)
writer.Write (s) 
writer.Close

objWshShell.Exec("c:\WINDOWS\explorer.exe "+SAVE_FOLDER)
'objWshShell.Exec("c:\Program Files\Internet Explorer\iexplore.exe "+SAVE_INDEX_PATH)

Set objWshShell=Nothing


'*********************************************************
'用途: WinMergeでフォルダ比較のレポートを出力キー操作。
'*********************************************************
Function FolderDiffReport(path)
	ActivateAndSendKeys TITLE_MAIN,"^{HOME}{DOWN}{UP}%{t}","r",100 '最初一件目を選択する
	ActivateAndSendKeys TITLE_SAVE_IDX,path,"%{S}{UP 4}{DOWN 2}{ENTER}",500 'Select save html & Wait for save
	ActivateAndSendKeys TITLE_OK,"","{ENTER}",100
End Function

'*********************************************************
'用途: WinMergeでフォルダ比較からファイル比較のレポートを出力キー操作。
'*********************************************************
Function FileDiffReport(path)
	ActivateAndSendKeys TITLE_MAIN,"","{ENTER}",WAIT_FOR_OPEN_DIFF 'Wait for open
	ActivateAndSendKeys TITLE_MAIN,"%{t}","r",100
	ActivateAndSendKeys TITLE_SAVE,"%{N}",path+"{ENTER}",WAIT_FOR_SAVE_DIFF 'Wait for save
	ActivateAndSendKeys TITLE_OK,"","{ENTER}",100  
	'ActivateAndSendKeys TITLE_MAIN,"","^w",WAIT_FOR_CLOSE_DIFF 'Wait for close | Closeよく失敗ある
	'ActivateAndSendKeys TITLE_MAIN,"","^{F4}",WAIT_FOR_CLOSE_DIFF 'Wait for close | Closeよく失敗ある
	ActivateAndSendKeys TITLE_MAIN,"%{W}","O",WAIT_FOR_CLOSE_DIFF 'Wait for close
End Function

Function GoNext()
	ActivateAndSendKeys TITLE_MAIN,"","{DOWN}",100
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

