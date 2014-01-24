/*
* Search file by conditions
*/
def dir = 'C:\\Documents and Settings\\HouTokki\\�f�X�N�g�b�v\\product-test-result\\�a�ʌ���-���\�����f�[�^�m�F'

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