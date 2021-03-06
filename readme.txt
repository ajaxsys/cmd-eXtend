【使い方】
方法１、一時セットアップ。
　　　　解凍後のcmdx.cmdを実行する。
方法２、セットアップ（環境変数設定）
　　　　同封の「setup.txt」を参照してください。

【マニュアル】
ヘルプは「man コマンド」で確認してください。

【コマンド一覧】
★：お勧めコマンド

cmd-eXtend          : ～～～共通的なコマンドツール～～～
│  cmdx-config.cmd : 環境設定変数
│  cmdx.cmd        : cmd-eXtendの一時セットアップコマンド
│  man.cmd         : ヘルプ。「manual」の略(like linux)
│
├─x-cmd           : ～～～CMDを拡張するコマンド群（No third-party）～～～
│      ~.cmd       : homeに戻る(like linux)
│      ~~.cmd      : desktopに戻る
│ ★   c.cmd       : 直接フォルダに移動する。例：「c フォルダパス」
│ ★   cp.cmd      : Copyコマンドの拡張、ファイルとフォルダも対応できる(like linux、関連コマンド：rm)。例：「cp a b」
│      cx.cmd      : cmd-eXtend所在のフォルダを開く(Cmd eXtend)
│ ★   e.cmd       : 現在のパスをエクスプローラーで開く。Win+Rで「e」コマンド実行すると、%USERPROFILE%が開く。
│      g.cmd       : ブラウザでサーチエンジンを開き、検索を行う。例：「g 検索内容」
│      gp.cmd      : (XP only)「Get Path」の略、エクスプローラーで選択された複数のファイル・フォルダのパス一覧を取得する
│      grep.cmd    : 複数ファイルに検索。例「grep key [files] [/S] [/I]」
│      ip.cmd      : ipを表示する。
│      ll.cmd      : dirと同様、パスにシングルスペース許容(like linux)
│      mc.cmd      : mkdir & cd 一個にするコマンド
│      n.cmd       : エディターでファイルを開く。例：「n file」
│ ★   q.cmd       : exitと同様(like postgre)
│ ★   rm.cmd      : del/rmdirコマンドの拡張、ファイルとフォルダも対応できる(like linux、関連コマンド：cp)。例：「rm a b c」
│      sv.cmd      : services.mscと同様
│ ★   tail.exe    : ファイルの変化を監視する
│ ★   touch.cmd   : ファイル存在すれば、更新日を現時点で更新。存在しなければ、空ファイル作成。例：「touch a.txt」
│      unzip.cmd   : 解凍。例：「unzip z.zip」
│ ★   which.cmd   : コマンドの位置を取得する。例：「which rm」
│ ★   x.cmd       : フォルダ下のexcel、word、pdfドキュメントをGrepする。例：「x Grep内容」
│      zip.cmd     : 圧縮、例：「zip z.zip a.txt folder...」。圧縮ファイル内容の表示、例：「zip z.zip」
│
├─x-java          : ～～～(tools/x-java-setup.cmdで初期化。Java＆Mavenが必要)～～～
│      base64.cmd  : Base64エンコーディング（Groovy版）
│      db.cmd      : derbyへ接続コマンド。
│      diffile.cmd : MD5判断でファイル一致するかと判定。
│      wget.cmd    : URLよりファイルをダウンロード。
│      encode.cmd  : ネットでファイル安全転送のため、ファイルを暗号化して後、Base64に変換する。
│      gsh.cmd     : GroovyのScriptを実行するShell。
│      gv.cmd      : GroovyのConsoleを出すコマンド。
│ ★   htm21.cmd   : Merge js/css files to a single html file.
│      jad.exe     : Javaクラスファイルをデコンパイル
│      jsh.cmd     : Javaソースを直接実行するShell。例：「jsh File.Java param1 param2」
│      MD5.cmd     : ファイルMD5取得。
│      resize.cmd  : 画像ファイルの幅を調整する。（フォルダとファイルサポート）
│      u.cmd       : 文字列のUnicodeを取得する。（native2ascii.exeを利用）
│ ★   wardiiff.cmd : War/Jar/Zipなどの圧縮ファイル（中身の.classもデコンパイルで）をDiffする。
│      ws.cmd      : 現フォルダをWebServerで共有(Web Server)
│
├─x-mvn           : ～～～mavenコマンドのショートカット～～～
│      m.cmd       : mvn clean package -Plocal
│      m2.cmd      : mavenのjarのフォルダを開く
│      mce.cmd     : mvn eclipse:clean eclipse:eclipse
│      me.cmd      : mvn eclipse:eclipseプロジェクト参照
│      mec.cmd     : mvn eclipse:clean
│      mee.cmd     : EclipseのJavaプロジェクト作成、プロジェクト参照しない。mvn eclipse:eclipse -Declipse.useProjectReferences=false
│      mew.cmd     : EclipseのWEBプロジェクト作成、プロジェクト参照しない。mvn eclipse:eclipse -Dwtpversion=2.0 -Declipse.useProjectReferences=false
│      mi.cmd      : mvn clean install
│      mj.cmd      : mvn jetty:run
│      mm.cmd      : mvn clean package -Pmock
│      mt.cmd      : mvn dependency:tree
│      mtv.cmd     : mvn dependency:tree -Dverbose
│      mtvc.cmd    : mvn create project that select from type list
│      mtvcj.cmd   : mvn create a jar project
│      mtvcw.cmd   : mvn create a war project
│
├─x-tools         : ～～～他のソフトウェアの拡張～～～
│      wm.cmd      : 「WinMerge folder diff report」の略。WinmergeのフォルダDiff結果のレポートを取得する。注意事項: tools\vbs\winmerge-folder-diff.vbsを参照
│
├─x-work          : ～～～個人仕事用のコマンド群(削除してください)～～～
│      cca.cmd     : CCA各サーバ一気に立ち上がる。
│      cdb.cmd     : 各環境のDBConection表示
│      d.cmd       : WinmergeでデスクトップファイルとProcenterのtmpフォルダのファイルとDiff
│      ex.cmd      : フォルダへリンク（EXecute）
│      htm.cmd     : フォルダへリンク（HTMlテスト）
│      jmt.cmd     : JMetter起動
│      l.cmd       : フォルダへリンク（Log）
│      lc.cmd      : サーバ立ち上がる（LoCk server）
│      mpl.cmd     : MPL各サーバ一気に立ち上がる（旧SPL）
│      mtc.cmd     : サーバ立ち上がる（My TomCat）
│      p.cmd       : ロカールのPostgresサーバに接続する
│      pc.cmd      : フォルダへリンク（ProCenter temp folder）
│      pcc.cmd     : ProCenter自動ログイン
│      procenter_cev.cmd : procenter自動ログインcmd版
│      px.cmd      : IEのProxyへの自動ログイン
│      pxy.cmd     : プロキシ再設定
│      nopxy.cmd   : プロキシ非設定
│      r.cmd       : よく使うコマンドを実行(Recent)
│      rr.cmd      : よく使うコマンドを編集(Recent Re-edit)
│      s2.cmd      : MPL各サーバ一気に立ち上がる（新SPL）
│      seq.cmd     : Seqサーバ一気に立ち上がる（新SPL）
│      ss.cmd      : サーバ立ち上がる（SecurityServer）
│      tc.cmd      : Tomcat立ち上がる
│      tcc.cmd     : TomcatのConfを確認
│      tcw.cmd     : Tomcatのwebappsを開く
│      w.cmd       : Workspaceへリンク
│
└─tools           : ～～～シェルに追加されてない、独立なツール～～～
    │  stop_beep.cmd                  : CMDにビービーの警告音を消す
    │  xdoc2txt.exe                   : xコマンドで使われExcelなど文書をtxtファイルに転換ツール。
    │
    ├─micro                          : ～～～便利なマクロ～～～
    │      hidemaru_hightlight.mac    : 秀丸で、Ctrl+1 でcursor所在の単語をハイライトする
    │
    ├─reg                            : ～～～便利なWindowsメニュー拡張～～～
    │  ★  Cmd_Here.reg               : フォルダの右メニューに「Cmd Here」を追加
    │  ★  OpenWithEditor.reg         : フォルダの右メニューに「XXXエディタで開く」を追加
    │
    └─vbs                            : ～～～Cmd-eXtendに使われたscripts～～～
            find.cmd                   : find.vbsのテスト
            find.vbs                   : ファイルと文字列に、正規表現で文字を検索する（１個目マッチした文字をリターン）
            findDesktop.vbs            : ~~コマンドに使用され（他国言語ＯＳ対応）
            GetPath.js                 : gpコマンドに使用され
            procenter_login.vbs        : pccコマンドに使用され
            proxy_login.vbs            : pxコマンドに使用され
            replace.cmd                : replace.vbsのテスト
            replace.vbs                : 文字列を正規表現で文字を検索＆置換する
            startup.vbs                : 起動に色々したいことをここに書いて、StartUpに追加
            unzip.vbs                  : unzipコマンドに使用され
            winmerge-folder-diff.vbs   : dfコマンドに使用され
            zip.vbs                    : zipコマンドに使用され
            ziplist.vbs                : zipコマンドに使用され

【注意事項】
①、Batchで利用の場合、call忘れないでください。
例：    call cp a.dat b.dat
        call rm b.dat

②、cmd-eXtendは下記のエンコーディングを利用する
        txt、vbs、jsファイル ：UTF-16（BOM付き）
        cmd,batファイル　　　：ASCII（欧文）
