
def targetDir = args[0]
//def targetDir = "c:/test/"
// TODO check null & dir exist

def dir=new File(targetDir)

//Pattern for groovy script
def vmExt = ".vm"

dir.eachFileRecurse({f->
    if (!f.getName().endsWith(vmExt)){
        println "~~~~~~~~~~~~ Ignored file :" + f.getName()
        return
    }
	println "~~~~~~~~~~~~ Start process file :" + f.getName()
	
    def targetText = ""
    
    f.eachLine({line, num->
    
      if (line.trim().startsWith("#") || ''.equals(line.trim()) ){
          //println "ignored : " + line
          // println "blank line"
          targetText += line+"\n"
          return;
      }
      //println "used : " + line
      // Java Src, Append vm infos
      line=line.replaceAll(/\s+\/\/=*=.*=*=\/\/$/, '') // Removed existed
      targetText += line + "    //=*= " + f.getName() + " Line:" + num + " =*=//"+"\n"
      
    })

    //output it
    f.setText(targetText, 'UTF-8')
})