//////////////////////★ソート順：パス　が必須★////////////////////////////////
def LIST = new File("D:/tmp/uList.txt").getText() // Copy from eclipse call hierarchy result: ルートで右クリック⇒展開された階層をコピー
def TREE = new File("D:/tmp/uTree.txt").getText() // Copy from eclipse call hierarchy result: ctrl+Aで右クリック⇒修飾名のコピー
def Rslt = new File("D:/tmp/uResult.txt")

// Replace LIST with TREE \t
def TreeLines=TREE.split("\n")
def maxTabs = ""

// get max tabs for tree
TreeLines.each{
    def tabs = it.find(/^\t*/)
    if (tabs.length()-maxTabs.length()>0) maxTabs=tabs
}

// format list
def RsltText = new StringBuilder()
LIST.split("\n").eachWithIndex{line , i ->
    if (line.length() > 1) {
        def tabs = TreeLines[i].find(/^\t*/)
        def subTabs=maxTabs.length()-tabs.length()+1
        RsltText.append(tabs).append("■").append("\t"*subTabs).append(line).append("\n")
    }
}

Rslt.setText(RsltText.toString())

return "OK"