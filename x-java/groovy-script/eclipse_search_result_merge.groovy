//////////////////////★ソート順：パス　が必須★////////////////////////////////
def LIST = "\n"+new File("D:/tmp/list.txt").getText()+"\n" // Copy from eclipse search result: list view
def TREE = "\n"+new File("D:/tmp/tree.txt").getText()+"\n" // Copy from eclipse search result: tree view
def Rslt = new File("D:/tmp/result.txt")

// Avoid project test-cmn > test.(Diff with tree view)
LIST=LIST.replaceAll('/',"\t")
LIST=LIST.split("\n").sort().join("\n").replaceAll("\t",'/')

// Replace TREE id with LIST id
LIST.split("\n").each{
    def its = it.split(" - ")
    if (its.length > 1) {
        def key = its[1].split(" ")[0] // anet_eu_ez/src/main/webapp/templates/BHS/err/ez - MovPcSyncErr.vm (2 個の一致) ⇒ MovPcSyncErr.vm
        TREE=TREE.replaceFirst("\n"+key+".*", "\n●" + it.replaceAll(key, key[0]+"★■▼"+key[1..-1])) // Use ★■▼ to mark replaced
    }
}

TREE=TREE.replaceAll(/\n[^0-9●].*|★■▼/, '');


// Add global num const
import static Constants.*

class Constants {
    static MYCONSTANT = 1
}

TREE=TREE.replaceAll( "\n●.*", {
        return it+"\t"+(MYCONSTANT++)+"\n"
    } 
)

// Remove multiline
TREE=TREE.replaceAll("\r|\n","\n")
TREE=TREE.replaceAll("\n+","\n")
Rslt.setText(TREE)

return "OK"