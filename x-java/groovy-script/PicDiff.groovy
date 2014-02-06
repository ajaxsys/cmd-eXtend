import javax.imageio.ImageIO;
import java.awt.Color;

/*
【用途】
    Folder A と Bの画像Diff
*/
IS_REMOVE_BINFILE=false // ★static with NON def. Set [true] if remove binary(.zip & .class) files. 

enum DiffType {PassedDiff,PicDiff,NoDiff,FolderDiff}

def picDiffFrom1 = new File(args[0])
def picDiffFrom2 = new File(args[1])
def picDiffTo = new File(args[2])

if (!picDiffFrom1.exists() || !picDiffFrom2.exists() || !picDiffTo.exists() ) {
    println "入力ファイル存在しません。(3ファイルとも存在する必要あり)"
    return -1;
}


// ArrayList
leftOnly = []
rightOnly = []
sameBin=[]
diffBin=[]
diffPic=[]
diffBinButSamePic=[]

//IS_DEL_ORIGINAL_FILE=FALSE;
println "Start:" + new Date().format("yyyy-MM-dd HH:mm:ss.SSSZ")

if (picDiffFrom1.isDirectory() && picDiffFrom2.isDirectory() && picDiffTo.isDirectory()) {
	doFolderDiff(picDiffFrom1,picDiffFrom2,picDiffTo)
} else {
	println "[WARN] input must be same as file/folder."
}


def noVisibleDiff=new File(picDiffTo, "_BinDiffButVisibleSame")

println "●Left Only files: ${leftOnly}"
println "●Right Only files: ${rightOnly}"
println "●Binary same files: ${sameBin}"
println "●Binary diff files: ${diffBin}"
diffBin.each{ 
	if (checkPicDiffPoint(it)==true) {
		diffPic.add(it)
	}else{
		def file=new File(it)
		def renameTo=file.getParent() + File.separator + "[NoDiff]"+file.getName()
		file.renameTo(renameTo)
		diffBinButSamePic.add(renameTo)
	}
}
println "☆Binary different but same view: ${diffBinButSamePic}"
println "★★★★ Pictures visible difference  ★★★★"
println "↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓"
println "  "+diffPic.join("\n  ")
println "↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑"
println "End:" + new Date().format("yyyy-MM-dd HH:mm:ss.SSSZ")

println "TODO: BackUp all result: ${picDiffTo} , and delete same visible files: ${diffBinButSamePic}"













def doFolderDiff(File A, File B, File C) {
	for (def fileName in uniqueFileNames(A,B) ) {
		File testA = new File(A, fileName)
		File testB = new File(B, fileName)
		File outputC = new File(C, fileName)
		
		DiffType type = getDiffType(testA, testB)
		println fileName + " : " + type
		switch (type) {
			case DiffType.PassedDiff:
			case DiffType.NoDiff:
				break;
			case DiffType.PicDiff:
				execCMD("""compare "${testA.getAbsolutePath()}" "${testB.getAbsolutePath()}" "${outputC.getAbsolutePath()}" """)
				diffBin.add(outputC.getAbsolutePath())
				break;
			case DiffType.FolderDiff:
				if (!outputC.exists())
					outputC.mkdir()
				doFolderDiff(testA, testB, outputC); // 再帰
				break;
		}
	}
}


DiffType getDiffType(File A, File B) {
	if (A.exists() && !B.exists()) {
		leftOnly.add(A.getAbsolutePath())
		return DiffType.PassedDiff
	}
	if (!A.exists() && B.exists()) {
		rightOnly.add(B.getAbsolutePath())
		return DiffType.PassedDiff
	}
	// A & B is all files
	if (A.isFile() && B.isFile()) {
		DiffType type = getFileType(A)
		// If pic , check if same file(use md5)
		if (type == DiffType.PicDiff) {
			if (A.length()==B.length() && generateMD5(A)==generateMD5(B)) {
				// Same binary file , do nothing
				sameBin.add(A.getAbsolutePath()+"==="+B.getAbsolutePath())
				type = DiffType.NoDiff
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
	def picExts = [".png",".jpg",".gif"]
	def fileName = file.getName().toLowerCase()
	// Check zip
	for (def ext in picExts ) {
		if ( fileName.endsWith(ext) )
			return DiffType.PicDiff
	}
	// Other files
	return DiffType.PassedDiff
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

// If picture contains a diff point(red/deep red)
def checkPicDiffPoint(String picPath){
	def img = ImageIO.read(new File(picPath));
	int width=img.getWidth();
	int height=img.getHeight();

	int SINGLE_DIFF_COLOR=new Color(244, 51, 75).getRGB()
	int DOUBLE_DIFF_COLOR=new Color(193, 0, 24).getRGB()

	for (int x=0; x<width; x++) {
		for ( int y=0; y<height; y++) {
			int rgb=img.getRGB(x,y)
			if ( SINGLE_DIFF_COLOR==rgb || DOUBLE_DIFF_COLOR==rgb ) {
				return true
			}
		}
	}
	return false
}