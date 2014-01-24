/*
* Edit the all of the files in a folder
*/
def folder = "D:/work/subscrcd-ok"

new File(folder).eachFile{
   it.setText(it.text.replaceFirst(/^/,"eva.mgt.seq=0101010001\n"))
}