import java.awt.Image
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

def USAGE= "resize from.jpg to.jpg width(int,px) \n For example: resize coffee.jpg coffee_200.jpg 200"

// 引数の処理
if( args.length < 3 ) {
	println USAGE
	return
}
def inputFile = new File(args[0])
def outputFile = new File(args[1])
def width = args[2]

if (!width.isInteger()) {
	println "    [WARN] $width is not an integer!"
	System.exit(9)
}
width = width.toInteger()

if( !inputFile.exists() ) {
	println "    [WARN] File ${inputFile} not exists!"
	System.exit(1)
}

try {
	// イメージの読み込みとリサイズ
	def img = ImageIO.read(inputFile)
	def imgScaled = img.getScaledInstance(width,-1,Image.SCALE_SMOOTH)

	// リサイズ(スケール)されたイメージを直接 ImageIO.write() できないので、処理を追加
	def img2 = new BufferedImage((int)imgScaled.width,(int)imgScaled.height,BufferedImage.TYPE_4BYTE_ABGR)
	def g = img2.getGraphics();
	g.drawImage(imgScaled,0,0,null)
	g.dispose()

	// リサイズされたイメージを保存
	ImageIO.write(img2,'PNG',outputFile)
} catch (Exception e){
	System.exit(1)
}