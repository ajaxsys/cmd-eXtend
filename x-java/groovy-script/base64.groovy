execute(args)
//test()

private String execute(args) {

def USAGE="""Usage :
  Encode: base64 inputfile [img] 
  Decode: base64 -d input output
"""

	// validation
	if (args.length <1 ){
		println USAGE
		return
	}

	def input=args[0]
	def isDecript = false
	if ("-d"==args[0]) {
		if (args.length>2){
			input=args[1]
			output=args[2]
			isDecript=true
		}else{
			println USAGE
			return
		}
	}

	def inputFile = new File(input)
	if (!inputFile.exists()) {
		println "File $input Not Existed.  "
		return
	}

	// Decoding file from base64
	if (isDecript) {
		def decodedBytes = inputFile.getText().replaceAll(/\n|\r/,"").decodeBase64()
		new File(output).setBytes(decodedBytes)
		return new String(decodedBytes)
	}

	// Encoding (for binary NG: new File(input).getText().bytes ) 
	def encodedData = inputFile.getBytes().encodeBase64().toString()
	// output
	if (args.length > 1 && "img".equals(args[1])){
		encodedData = """<img src="data:${getImgType(input)};base64,$encodedData" />"""
		println encodedData // can not decode
	}
	else{
		print encodedData
	}
	return encodedData
	
}

private String getImgType(path){
	def mimemapping = Util.getMIMEByFilePath(path)
	if (mimemapping=="") mimemapping="image/jpeg"
	return mimemapping
}

private void test() {
	// init
	def FILE_IN = "xsdfsts123d342fs54h6laejrelxdf.png"
	def FILE_OUT = "base64_"+FILE_IN
	def FILE_RETURN = "return_" + FILE_IN
	def TEXT = "abcdedfg"

	def tmp_in = new File(FILE_IN)
	def tmp_out = new File(FILE_OUT)

	tmp_in.setText(TEXT)

	// encode
	println """Text "abcdedfg" base64 is:"""
	encode = execute([FILE_IN].toArray())

	tmp_out.setText(encode)
	// decode
	decode = execute(["-d", FILE_OUT, FILE_RETURN].toArray())
	assert decode == TEXT

	// encode(image mode)
	println """\nText "abcdedfg" base64 img tag is:"""
	assert execute([FILE_IN, "img"].toArray()) == """<img src="data:image/png;base64,$encode" />"""

	// clear
	tmp_in.delete()
	tmp_out.delete()
	new File(FILE_RETURN).delete()

	println "\n Test Successfully !"
}