/*A Simple Web Server*/

// validation
if (args.length>0 && args[0].isInteger() ){
	port=args[0].toInteger() 
} else{
	port=80
}

// java.net.ServerSocket
ServerSocket s

try {
	// create the main server socket
	s = new ServerSocket(port)
	println "Webserver starting up on port $port:  http://localhost:$port/"
	println "(press ctrl-c to exit)"
} catch (Exception e) {
	println "Error: " + e
	return;
}

workspace = new File(".").getCanonicalPath()
println "Root directory: $workspace"
println "Waiting for connection"
while (true) {
	// wait for a connection
	Socket remote = s.accept()
	// remote is now the connected socket
	def input = new BufferedReader(new InputStreamReader(remote.getInputStream()))
	def ostream = remote.getOutputStream() // java.net.SocketOutputStream
	def otext = new BufferedWriter(new OutputStreamWriter(ostream))

	try {

		// read the data sent. We basically ignore it,
		// stop reading once a blank line is hit. This
		// blank line signals the end of the client HTTP
		// headers.
		def header = getReqHeader(input)
		if ("".equals(header)) {
			// fix firefox bugs? of last empty request
			throw new RuntimeException("Socket closed.")
		}
		def uri = getURI(header)

		println "======================"
		println header // log

		if (existURI(uri)){
			def mime= Util.getMIMEByFilePath(uri)
			printOKResHeader(otext, mime)
			ostream.write(new File(workspace + uri).getBytes())
			ostream.flush() // Send file as binary
			println "200 - $mime - Path=$uri"
		}else{
			print404ResHeader(otext)
			otext.write("404 Page Not Found. \u30da\u30fc\u30b8\u304c\u898b\u3064\u304b\u308a\u307e\u305b\u3093\u3002 \u627E\u4E0D\u5230\u753B\u9762\u3002 (Path=$uri)\n")
			otext.flush()
			println "404 - Path=$uri"
		}

		// Send the response
		// Send the headers
		println "======================"

	
	} catch (Exception e) {
		showMemory()
		println e.getMessage()
	} finally {
		// close
		otext.close()
		ostream.close()
		input.close()
		remote.close()
	}
}

private String getThisScriptFile() {
	def cp = this.getClass().classLoader.resourceLoader.loadGroovySource(getClass().name)
	if (!"file".equalsIgnoreCase(cp.getProtocol()))
		throw new IllegalStateException("Main class is not stored in a file.");
	return cp.getPath();
}

private boolean existURI(uri) {
	if ("".equals(uri) || uri.endsWith('/'))
		return false;
	return new File(workspace + uri).exists()
}

private String getReqHeader(input){
	def sb = new StringBuilder("")
	String str = input.readLine()
	while (str!=null && !"".equals(str)) {
		sb.append( str + "\n" )
		str = input.readLine()
	}
	return sb.toString()
}

private void printOKResHeader(out, mime){
	if (!mime) mime="text/html"

	out.write("HTTP/1.1 200 OK\n")
	out.write("Content-Type: $mime\n")
	// this blank line signals the end of the headers
	out.write("\n")
	out.flush()
}

private void print404ResHeader(out){
	out.write("HTTP/1.1 404 Not Found\n")
	out.write("Server: FlyAway\n")
	// this blank line signals the end of the headers
	out.write("\n")
	out.flush()

}

private void showMemory(){

	int mb = 1024*1024;

	//Getting the runtime reference from system
	Runtime runtime = Runtime.getRuntime();

	//Print used memory
	println "    Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb + "M"
 	//Print free memory
	println "    Free Memory:" + runtime.freeMemory() / mb + "M"
	//Print total available memory
	println "   Total Memory:" + runtime.totalMemory() / mb + "M"
	//Print Maximum available memory
	println "     Max Memory:" + runtime.maxMemory() / mb + "M"

}

// eg. : "GET /test.html?a=b#ccc HTTP/1.1" to get "./test.html"
private String getURI(header) {
	def finder = (   header =~     /GET.*|POST.*|PUT.*|DELETE.*/    )
	return (finder.count > 0) ? "."+finder[0].replaceAll(/^\w+ /,"").replaceAll(/ .*/,"").replaceAll(/\?.*|#.*/,"") : "" 
}