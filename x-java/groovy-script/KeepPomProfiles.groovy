/*
 * Keep given pom.xml profile nodes.
 * Usage: KeepPomProfiles.cmd local product
 */
import groovy.xml.DOMBuilder
import groovy.xml.dom.DOMCategory

def xmlFile = new File(args[0])
String[] keepKeys = args.tail()

def reader  = new StringReader(xmlFile.getText())
def root    = DOMBuilder.parse(reader)
def records = root.documentElement

def isDeleted = false

use (DOMCategory) {

	profiles = records.profiles.profile
	println "[INFO] Found ${profiles.size()} profiles. "

	profiles.each {
		String profileID = it.id.text()

		def toBeDel = true
		for (def key : keepKeys) {
			if ( key.equals(profileID) ) {
				toBeDel=false
				break
			}
		}
		if (toBeDel){
			println "[INFO] Delete node: " + profileID // + " - " + it
			it.replaceNode{}

			isDeleted = true
		}
	}

}

if (isDeleted == null) {
	println "[WARN] Node $keepKeys NOT FOUND!"
}

def result = groovy.xml.XmlUtil.serialize(records)
result=result.replaceAll(/\r/,'') // Remove CR(\r) , Keep LF(\n) only
//println result
xmlFile.setText(result)
