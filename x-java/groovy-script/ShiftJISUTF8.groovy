/*
* Search file by conditions
*/
def dir = 'D:/shiftjs'

new File(dir).eachFileRecurse{
    // it.getText() supports UTF-8 & default system encoidng
    if (it.isFile()) it.setText(it.getText(),"UTF-8");
}
