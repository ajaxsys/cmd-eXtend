// 正規表現で分割
// 使用例、月単位でPostgres結果の年月日「| 20140101 」を分割：
//     gsh bunkatu.groovy step2_fixed.sql_out_sample "\| (201[0-9]{3})([0-9]{2}) "
// 注意：正規表現のGroup 1はファイル名となる

import groovy.transform.Field

if (args.length < 2){
    println 'Usage: gsh thisScript inputFile "Regexp(FileName)String" [int_FileNameGroupNo]'
    return 
}
def f = new File(args[0])
def regexp = args[1]
def fileNameGroupNo = 1

if (args.length > 2){
    fileNameGroupNo = args[2]
}


@Field map = [split_not_exist: createEmptyFile("split_not_exist.txt")]
@Field mapBuff = [split_not_exist: new StringBuilder()]

if (f.exists()){
    
    f.eachLine {ln -> 
        m = ln =~ regexp
        //print m.size()
        if ( m.size() > 0  ) {
            def month = m[0][fileNameGroupNo];
            output("split_" + month, ln)
        } else {
            output("split_not_exist", ln)
        }
        
    }
    // Flush last
    for (m in map) {
        File ff = m.value
        ff.append( mapBuff.get( m.key ) )
    }

} else {
    println "file not found" + f
}

def output(fileName, ln){
    fileName = fileName + ".txt"

    def mf
    def mb

    if (!map.containsKey(fileName)){
        mf = createEmptyFile(fileName)
        map.put(fileName, mf)
        mb = new StringBuilder()
        mapBuff.put(fileName, mb)
    } else {
        mf = map.get(fileName)
        mb = mapBuff.get(fileName)
    }

    mb.append(ln).append("\n")

    if (mb.length() > 1048576){
        mf.append( mb.toString() )
        mb.setLength(0)
    }

}

def createEmptyFile(fileName){
    def notExistFile = new File(fileName)
    notExistFile.delete()
    notExistFile.createNewFile()
    return notExistFile
}