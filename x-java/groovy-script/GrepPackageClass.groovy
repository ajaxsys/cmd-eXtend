/*
 * このスクリプトは、指定したパッケージまたはクラス名「searchPackageOrClass」でJavaソースにGrepし、
 * importまたは、直接パッケージ指定された箇所を取得する。
 *
 * ※ classの場合、そのまま検索
 * ※ packageの場合、package.[_A-Z]で検索
 *
 * 検索結果は、秀丸のファイルGrepの結果の形で保存する。
 *
 */
File SOURCE_FOLDER = new File('D:/eclipse/ws_trunk_sub/mpl_test_gmn/')// D:/eclipse/wsmaple-trunk
String SOURCE_FILE_TYPE = '.java'
File OUTPUT_FOLDER = new File('D:/tmp/ooo/')

// ============================== Step 1 , process input lists ==============================
String searchPackageOrClass="""
java.util.List
org.openqa.selenium.WebDriver
com.kddi.fmbc.mpl.test.util
"""

// Change to regexp keys
List regexSearchKey = getSearchRegexpKey(searchPackageOrClass)

// clear output dir
clearFolder(OUTPUT_FOLDER)

// ============================== Step2 Grep all keys ==============================
def sbClass = new StringBuilder()
def sbMethod = new StringBuilder()

println "Start at :" + new Date()
println "Grep file with extention `$SOURCE_FILE_TYPE` in `$SOURCE_FOLDER`."
println "Grep keys: `$regexSearchKey`"
SOURCE_FOLDER.eachFileRecurse{
    if(it.isFile() && it.name.endsWith(SOURCE_FILE_TYPE)) {
        def nowFile = it
        regexSearchKey.each{
            def searchKey = /.*$it.*/
            def outputFile = new File(OUTPUT_FOLDER, it+"_result.txt")
            
            // Line mode (Whole File mode search spend more time VS line mode!)
            nowFile.eachLine{line, lineNumber ->
                if (line =~ searchKey) {
                    // HidemaruMode: Path(4): import jp.co.xxx.sss.db.rdb.tx.ITxProc
                    sbClass.append(nowFile.getAbsolutePath()).append(":($lineNumber) $line\n") 
                }
            }

            // ============================== Step3 get methods ==============================
            if (sbClass.length() > 0) {
                /* TODO
                def clazz = getClassNameList(nowFile,searchKey)
                clazz.each {
                    // Static method: Class.method(...)
                    sbMethod.append(staticMethods)
                    // Instance method: Class var = ...  var.method(...)
                    sbMethod.append(instanceMethods)
                }
                */
                
            }

            outputFile.append(sbClass.toString())
            outputFile.append(sbMethod.toString()) // TODO
            
            sbClass.setLength(0)
            sbMethod.setLength(0) // TODO
        }
    }
}
println "Process complete! Please check ouput folder `$OUTPUT_FOLDER`"
println "End at   :" + new Date()




// ============================== Functions ==============================

def getSearchRegexpKey(packageOrClassName){
    def names = new ArrayList()
    packageOrClassName.split("\n").each{
        if (it.length()>0) 
            if (it.matches(/^.*\.[_A-Z]\w+$/))
                names.add(it) // class
            else
                names.add(it + '.[_A-Z]') // package
    }
    return names
}

def clearFolder(folder) {
    if (folder.exists())
        folder.deleteDir()
    folder.mkdirs()
    if (folder.list().length>0)
       throw new RuntimeException("[FATAL] Clear folder [$folder] Failed.")
}
