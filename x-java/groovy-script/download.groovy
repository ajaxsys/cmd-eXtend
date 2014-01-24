/*
 * Use gvc.cmd to compile to classes in [groovy-script-classes] folder
 */
def USAGE="gsh download.groovy http://www.google.co.jp/images/srpr/logo3w.png output.gif"
// validation
if (args.length<2) {
	println USAGE
	return
}

Util.setProxy()

def file = new File(args[1])
use (FileBinaryCategory)
{
file << args[0].toURL()
}

class FileBinaryCategory
{
  def static leftShift(File a_file, URL a_url)
  {
    def input
    def output
    try
    {
      input = a_url.openStream()
      output = new BufferedOutputStream(new FileOutputStream(a_file))
      output << input
    }
    finally
    {
       input?.close()
       output?.close()
    }
  }
}

/*
A little less verbose FileBinaryCategory 

class FileBinaryCategory{    
    def static leftShift(File file, URL url){    
       url.withInputStream {is->
            file.withOutputStream {os->
                def bs = new BufferedOutputStream( os )
                bs << is                    
            }
        } 
    }    
}

*/