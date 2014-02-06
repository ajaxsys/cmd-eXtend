/*
【前提】環境変数に下記のコマンドサポートが必要：
    jad  : Javaファイルをデコンパイル。			Usage: jad -p xxx.class
    rm   : ファイルかディレクトリか問わず、削除する。	Usage: rm file/folder
    unzip: zip,jar,warファイルを解凍する。		Usage: unzip zipFile unzippedFolder
    
【用途】
    war A と BのDiff
    １ war A解凍、war B解凍
    ２ Diff AとB（子フォルダ含み）
    ３ １) 差分の中身がテキストの場合、飛ばす
　　   ２) 差分の中身が圧縮ファイル（Zip・Jar）の場合、同名（拡張式含み）解凍し、Diff一覧に追加する
　　   ３) 差分の中身がClassファイルの場合、Jadでデコンパイルする
　　　　※　非テキストファイル自体が削除されるか、IS_REMOVE_BINFILEで設定
*/
IS_REMOVE_BINFILE=false // ★static with NON def. Set [true] if remove binary(.zip & .class) files. 

enum DiffType {PassedDiff,ZipDiff,ClassDiff,FolderDiff}

def warDiffFrom = new File(args[0])
def warDiffTo = new File(args[1])

if (!warDiffFrom.exists() || !warDiffTo.exists() ) {
    println "入力ファイル存在しません。"
    return -1;
}


//IS_DEL_ORIGINAL_FILE=FALSE;
println "Start:" + new Date().format("yyyy-MM-dd HH:mm:ss.SSSZ")

if (warDiffFrom.isFile() && warDiffTo.isFile()) {
	def folderA = unzip(warDiffFrom)
	def folderB = unzip(warDiffTo)
	doFolderDiff(folderA,folderB);
} else if (warDiffFrom.isDirectory() && warDiffTo.isDirectory()) {
	doFolderDiff(warDiffFrom,warDiffTo)
} else {
	println "[WARN] input must be same as file/folder."
}


println "End:" + new Date().format("yyyy-MM-dd HH:mm:ss.SSSZ")
println "//TODO：WinMergeでDiffレポートを取得して下さい。"

def doFolderDiff(File A, File B) {
	for (def fileName in uniqueFileNames(A,B) ) {
		File testA = new File(A, fileName)
		File testB = new File(B, fileName)
		
		DiffType type = getDiffType(testA, testB)
		println fileName + " : " + type
		switch (type) {
			case DiffType.PassedDiff:
				break;
			case DiffType.ZipDiff:
				def unziped_folderA = unzip(testA)
				def unziped_folderB = unzip(testB)
				doFolderDiff(unziped_folderA,unziped_folderB); // 再帰
				checkIfRemoveBinaryFile(testA, testB)
				break;
			case DiffType.ClassDiff:
				deCompileClass(testA)
				deCompileClass(testB)
				checkIfRemoveBinaryFile(testA,testB)
				break;
			case DiffType.FolderDiff:
				doFolderDiff(testA, testB); // 再帰
				break;
		}
	}
}

def deCompileClass(File classFile) {
	def folder = classFile.getAbsoluteFile().getParent()
	def fileName = classFile.getName()
	execCMD("cmd /c pushd \"${folder}\" & jad -p \"${fileName}\" > \"${fileName}.java\" & popd");
}

DiffType getDiffType(File A, File B) {
	if (!A.exists() || !B.exists())
		return DiffType.PassedDiff
	// A & B is all files
	if (A.isFile() && B.isFile()) {
		DiffType type = getFileType(A)
		// If zip or class , check if same file(use md5)
		if (type != DiffType.PassedDiff) {
			if (A.length()==B.length() && generateMD5(A)==generateMD5(B)) {
				// Same file , do nothing
				return DiffType.PassedDiff
			}
		}
		return type
	}
	// Folder
	if (A.isDirectory() && B.isDirectory()) {
		return DiffType.FolderDiff
	}
	// Single File & Single Folder
	return DiffType.PassedDiff

}	

DiffType getFileType(File file) {
	def zipExts = [".zip",".jar",".war",".ear"]
	def clzExt  = ".class"
	def fileName = file.getName().toLowerCase()
	// Check zip
	for (def ext in zipExts ) {
		if ( fileName.endsWith(ext) )
			return DiffType.ZipDiff
	}
	// Check class
	if ( fileName.endsWith(clzExt) )
		return DiffType.ClassDiff
	// Normal text
	return DiffType.PassedDiff
}

File unzip(File war){
	def path=war.getAbsolutePath()
	//def pathTo=war.getAbsoluteFile().getParent()
	def pathTo="${path}_dir"

	execCMD("cmd /c if exist \"${pathTo}\" ( rm \"${pathTo}\" )" )
	execCMD("cmd /c unzip \"${path}\" \"${pathTo}\"" )

	return new File(pathTo)
}

def execCMD(cmd){
	// Need "cmd /c " before any commands
	println "Execute: $cmd"
	cmd.execute().text // Not: cmd.execute(). Not: p.waitFor()
	
	// In some command like unzip large files, process.waitFor() never returns
	// Use cmd.execute().text to wait command executed
	//Process p = cmd.execute()
	//p.waitFor()
}

def uniqueFileNames(File A,File B) {
	def list = []

	list.addAll(A.list())
	list.addAll(B.list())
	return list.unique()
}

def generateMD5(final file) {
	java.security.MessageDigest digest = java.security.MessageDigest.getInstance("MD5")
	file.withInputStream(){is->
		byte[] buffer = new byte[8192]
		int read = 0
		while( (read = is.read(buffer)) > 0) {
			digest.update(buffer, 0, read);
		}
	}
	byte[] md5sum = digest.digest()
	BigInteger bigInt = new BigInteger(1, md5sum)
	def md5String=bigInt.toString(16)
	println "File ${file} md5: ${md5String}."
	return md5String
}

def checkIfRemoveBinaryFile(File a,File b) {
	if (IS_REMOVE_BINFILE) {
		a.delete()
		b.delete()
	}
}
