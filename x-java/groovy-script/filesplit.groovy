/*
* Split file with blank lines (Enter keys)
* (Use the Editor to prepare the input style)
*/
def input = "d:/work/kicker-subscrcd.properties"
def outfolder = "D:/work/"
def nameStart="kicker-subscrcd-"
def nameEnd=".properties"
def conter = 1;
def lineBef = ""
def fw = null

new File(input).eachLine{
    if ( lineBef.equals("") && !it.equals("") ){
        fw=new File(outfolder + nameStart+conter.toString().padLeft(3,"0")+nameEnd).newWriter()
        conter++;
    }

    if (it.equals("")) {
        if (fw!=null) {
            fw.close()
            fw = null
        }
    }else{
        fw.writeLine(it)
    }

    // println it
    lineBef = it
}