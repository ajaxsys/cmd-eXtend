// validate
def inputFile = new File(args[0])

if (!inputFile.exists()) {
    println "[ERROR] File $inputFile not exists."
    return
}

// load by system default encoding
def text = inputFile.getText()

// Check html encoding
def finder = (   text =~     /<meta.*?content-type.*?>/    )
def charset="UTF-8"
(0..<finder.count).each{
	newCharset = getAttribute(finder[it], "content").split(";")[1].split("=")[1]
	println "charset: $newCharset"
	if (newCharset?.toUpperCase() != "UTF-8") {
		println "[INFO] Reset encoding from [" + newCharset + "] to [UTF-8]"
		charset=newCharset
		text = inputFile.getText(charset)
		text = text.replace(finder[it], """<meta http-equiv="content-type" content="text/html; charset=UTF-8" />""")
	}
}

// find & load script
// (?<=regex):Read After ; (?=regex): Read Before
// finder = /(?<=<script.{1,100}src=).+?(?=>.*?<\/script>)/

finder = (   text =~     /<script.*?src.*?>/    )

(0..<finder.count).each{
	def uri = getAttribute(finder[it],"src")
	def target = getUriText(uri,charset)

	if (target==null){
		println "[WARN] Failed to load js :" + uri
	}else{
		println "[INFO] Reset js file :" + uri
		def endTag='';
		if (finder[it].endsWith("/>"))
			endTag = '</script>'
		text = text.replace(finder[it], """<script type="text/javascript">${target}${endTag}""" )
	}
}

// find & load css . eg <link rel="stylesheet" href="codemirror.css">
finder = (   text =~     /<link.*?type.*?text\/css.*?>|<link.*?rel.*?stylesheet.*?>/    )

(0..<finder.count).each{
	def uri = getAttribute(finder[it],"href")
	def target = getUriText(uri,charset)

	if (target==null){
		println "[WARN] Failed to load css :" + uri
	}else{
		println "[INFO] Reset css file :" + uri
		text = text.replace(finder[it], """<style type="text/css">${target}</style>""");
	}
}


// merge & output
def outputFile = new File("allin1_"+args[0])
outputFile.setText(text,"UTF-8")
println "[INFO] Congratulations, Merge html file successfully!"


private String getUriText(uri, charset) {
	if (uri == null)
		return null;
	uri = uri.trim();

	if (uri.startsWith("http://") || uri.startsWith("https://") || uri.startsWith("ftp://")) {
		def tmp = File.createTempFile("test",null)
		try {
			use (FileBinaryCategory){ tmp << uri.toURL() }
			return tmp.getText(charset)
 		}catch (Exception e){
			return null
		}finally {
			tmp.delete()
		}
	} else {
		return new File(uri).getText(charset);
	}
}

// functions 
private String getAttribute(xmltag, attr) {
	// WARN: Use replaceAll , NOT replace
	xmltag = xmltag.replaceAll(/\n/,"").trim()
	xmltag = xmltag.endsWith("/>") ? xmltag : xmltag.replaceAll(/>$/,"/>")

	def xmldoc = new XmlParser().parseText(xmltag)
	return xmldoc.attribute(attr)
}



class FileBinaryCategory {
    def static leftShift(File a_file, URL a_url) {
		def input
		def output
		try {
		  input = a_url.openStream()
		  output = new BufferedOutputStream(new FileOutputStream(a_file))
		  output << input
		} finally {
		   input?.close()
		   output?.close()
		}
	}
}



/*
def s = "Programming with Groovy is fun!"

assert "Programming with Groovy rocks!" == s.replaceAll(~/is fun!/, "rocks!")  // Groovy extension to String.
assert "Programming with Groovy is awesome." == s.replaceAll("fun!", "awesome.")  // java.lang.String.replaceAll.

// Replace found String with result of closure.
def replaced = s.replaceAll(/fun/) {
    def list = ['awesome', 'cool', 'okay']
    list[new Random().nextInt(list.size())]
}
assert [
    "Programming with Groovy is awesome!",
    "Programming with Groovy is cool!",
    "Programming with Groovy is okay!"
].contains(replaced)
 
// Use closure to replace text and use grouping. 
// First closure parameter is complete string and following
// parameters are the groups.
def txt = "Generated on 30-10-2009 with Groovy."
def replacedTxt = txt.replaceAll(/.*(\d{2}-\d{2}-\d{4}).*(Gr.*)./) { all, date, lang ->
    def dateObj = Date.parse('dd-MM-yyyy', date)
    "The text '$all' was created with $lang on a ${dateObj.format('EEEE')}."
}

*/