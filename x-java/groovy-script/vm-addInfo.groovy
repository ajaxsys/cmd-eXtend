//def targetDir = "c:/test/"
def targetDir = args[0] // Always use current directory
// TODO check null & dir exist

def targetTask = 'add' // or 'clear'
if (args.length>1){
    println 'MODE:' + args[1]
    targetTask = args[1]
}

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
    
    f.eachLine('UTF-8', {line, num->
    
    //if (line.trim().startsWith("#") || ''.equals(line.trim()) ){
    //if (''.equals(line.trim()) ){
    if ((!line.trim().startsWith("#if")  &&
         !line.trim().startsWith("#else") &&
         !line.trim().startsWith("#foreach") &&
         line.trim().startsWith("#"))
      || ''.equals(line.trim())  ){

          //println "ignored : " + line
          // println "blank line"
          targetText += line+"\n"
          return;
      }

      //println "used : " + line
      // Java Src, Append vm infos
      line=line.replaceAll(/\s+\/\/=*=.*=*=\/\/$/, '') // Removed existed

      if (targetTask=='clear'){
          // delete mode
          targetText += line + "\n"
      } else {
          // Append mode
          targetText += line + "    //=*= " + f.getName() + " Line:" + num + " =*=//"+"\n"
      }

    })

    //output it
    f.setText(targetText, 'UTF-8')
})