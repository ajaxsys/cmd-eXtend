//////////////////////★ソート順：パス　が必須★////////////////////////////////
def TREE = new File("D:/tmp/tree.txt").getText() // Copy from eclipse call hierarchy result: ルートで右クリック⇒展開された階層をコピー
def Rslt = new File("D:/tmp/tree_result.txt")

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
def rootCounter = 0
TREE.split("\n").eachWithIndex{line , i ->
    line=line.replaceAll(/^\t*/,'');
    if (line.length() > 1) {
        def tabs = TreeLines[i].find(/^\t*/)
        def subTabs=maxTabs.length()-tabs.length()+1
        
        def rootCounterAppend='\t'
        if (tabs.length()==0){
        	rootCounter++
        	rootCounterAppend = rootCounter + rootCounterAppend
        }
        RsltText.append(rootCounterAppend).append(tabs).append("■").append("\t"*subTabs).append(line).append("\n")
    }
}

Rslt.setText(RsltText.toString())

return "OK"