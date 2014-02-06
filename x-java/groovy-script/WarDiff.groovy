/*
�y�O��z���ϐ��ɉ��L�̃R�}���h�T�|�[�g���K�v�F
    jad  : Java�t�@�C�����f�R���p�C���B			Usage: jad -p xxx.class
    rm   : �t�@�C�����f�B���N�g������킸�A�폜����B	Usage: rm file/folder
    unzip: zip,jar,war�t�@�C�����𓀂���B		Usage: unzip zipFile unzippedFolder
    
�y�p�r�z
    war A �� B��Diff
    �P war A�𓀁Awar B��
    �Q Diff A��B�i�q�t�H���_�܂݁j
    �R �P) �����̒��g���e�L�X�g�̏ꍇ�A��΂�
�@�@   �Q) �����̒��g�����k�t�@�C���iZip�EJar�j�̏ꍇ�A�����i�g�����܂݁j�𓀂��ADiff�ꗗ�ɒǉ�����
�@�@   �R) �����̒��g��Class�t�@�C���̏ꍇ�AJad�Ńf�R���p�C������
�@�@�@�@���@��e�L�X�g�t�@�C�����̂��폜����邩�AIS_REMOVE_BINFILE�Őݒ�
*/
IS_REMOVE_BINFILE=false // ��static with NON def. Set [true] if remove binary(.zip & .class) files. 

enum DiffType {PassedDiff,ZipDiff,ClassDiff,FolderDiff}

def warDiffFrom = new File(args[0])
def warDiffTo = new File(args[1])

if (!warDiffFrom.exists() || !warDiffTo.exists() ) {
    println "���̓t�@�C�����݂��܂���B"
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
println "//TODO�FWinMerge��Diff���|�[�g���擾���ĉ������B"

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
				doFolderDiff(unziped_folderA,unziped_folderB); // �ċA
				checkIfRemoveBinaryFile(testA, testB)
				break;
			case DiffType.ClassDiff:
				deCompileClass(testA)
				deCompileClass(testB)
				checkIfRemoveBinaryFile(testA,testB)
				break;
			case DiffType.FolderDiff:
				doFolderDiff(testA, testB); // �ċA
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
