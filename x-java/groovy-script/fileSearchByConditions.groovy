/*
* Search file by conditions
*/
def dir = 'C:\\Documents and Settings\\HouTokki\\デスクトップ\\product-test-result\\疎通結果-性能試験データ確認'

new File(dir).eachFile{
    def txt = it.getText() 
    def auids = txt.findAll(/<eva:auid><!\[CDATA\[.+\]\]><\/eva:auid>/)
    def subscrs = txt.findAll(/<eva:kanyushacd><!\[CDATA\[.+\]\]><\/eva:kanyushacd>/)
    if (auids.size() > 0 && subscrs.size() > 0 ){
        def filename=it.name
        println "---------------file:$filename--------"
        println auids
        println subscrs
    }
}