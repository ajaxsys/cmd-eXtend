// Utilities

def static getMIMEByFilePath(path) {
	def ext=getExtension(path).toLowerCase()
	def xml = new XmlSlurper().parseText(getMIMEMapping())
	def mimemapping = xml.mimemapping.find{ it.extension[0]==ext }
	if (mimemapping)
		return mimemapping.mimetype[0]
	else
		return ""
}

def static getMIMEMapping() {
	def cp=getThisScriptPath()
	new File(cp+"/mime.xml").getText()
}

// Groovy Only . Not support pure java
def static getThisScriptPath() {
	def thisClass = new Util().getClass()
	def srcURI = thisClass.classLoader.resourceLoader.loadGroovySource(thisClass.name)
	if (!"file".equalsIgnoreCase(srcURI.getProtocol()))
		throw new IllegalStateException("This class is not stored in a file.");
	return new File(srcURI.getPath()).getParent()
}

def static getResourcePath(resourceName) {
	def thisClass = new Util().getClass()
	def srcURI = thisClass.classLoader.getResource(resourceName)
	if (!"file".equalsIgnoreCase(srcURI.getProtocol()))
		throw new IllegalStateException("This class is not stored in a file.");
	return srcURI.getPath()
}


// tools from org.apache.commons.io.FilenameUtils
def static String getExtension(String filename) {
    if (filename == null) {
        return null;
    }
    int index = indexOfExtension(filename);
    if (index == -1) {
        return "";
    } else {
        return filename.substring(index + 1);
    }
}
def static int indexOfExtension(String filename) {
    if (filename == null) {
        return -1;
    }
    int extensionPos = filename.lastIndexOf(".");
    int lastSeparator = indexOfLastSeparator(filename);
    return (lastSeparator > extensionPos ? -1 : extensionPos);
}
def static int indexOfLastSeparator(String filename) {
    if (filename == null) {
        return -1;
    }
    int lastUnixPos = filename.lastIndexOf("/");
    int lastWindowsPos = filename.lastIndexOf("\\");
    return Math.max(lastUnixPos, lastWindowsPos);
}

// set proxy by proxy.properties
def static void setProxy(){
	def props = new Properties()
	new File(Util.getResourcePath("proxy.properties")).withInputStream {
		stream -> props.load(stream)
	}

	if ( "true" != props.getProperty("proxySet") )
		return

	Enumeration e = props.propertyNames();
	while (e.hasMoreElements()) {
		String key = (String) e.nextElement();
		//System.out.println(key + " -- " + props.getProperty(key));
		System.setProperty(key, props.getProperty(key));
	}

	final String authUser = props.getProperty("http.proxyUser");
	final String authPassword = props.getProperty("http.proxyPassword");
	Authenticator.setDefault(
	   new Authenticator() {
	      public PasswordAuthentication getPasswordAuthentication() {
	         return new PasswordAuthentication(
	               authUser, authPassword.toCharArray());
	      }
	   }
	);
}