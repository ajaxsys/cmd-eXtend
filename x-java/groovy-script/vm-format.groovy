import static C.*

class C {
    static final beginTag=["#if", "#while", "#foreach"] // indent +1
    static final midTag=["#elseif", "#else"]  // indent keep
    static final endTag=["#end"] // indent -1
    
    static final getMatchTags(line, tags){
        //println tags
        def c=[]
        line = line.toLowerCase()
        tags.each({tag->
            //println line + " find in " + tag + " result " + (line ==~ /${tag}\b/ )
            if(line.find( /^${tag}\b/ ) != null ||
			   line.find( /\s${tag}\b/ ) != null || 
			   line.find( /\b${tag}$/ ) != null || 
			   line.find( /\b${tag}\b/ ) != null){
                c+=tag
            }
        })
        return c
    }
    private static final getTagsNum(line, tags){
        return getMatchTags(line, tags).size()
    }
    
    static final getTags(line){
        return getMatchTags(line, beginTag + midTag + endTag)
    }
    static final isStartsTag(tag){
        return getTagsNum(tag, beginTag)==1
    }
    static final isEndTag(tag){
        return getTagsNum(tag, endTag)==1
    }
    static final isMidTag(tag){
        return getTagsNum(tag, midTag)==1
    }
    
    static final appendIndent(depth, line){
        //println "====================" + depth + " line" + line
        def FOUR_TABS= "    "
        def NEW_LINE="\$LF"
        
        if (depth<1) depth=1
        
        //println "-----" + FOUR_TABS.multiply(depth-1) + line.trim() + ( line.endsWith(NEW_LINE) ? "" : (FOUR_TABS+NEW_LINE) ) + "\n"
        return FOUR_TABS.multiply(depth-1) + line.trim() + 
            (line.endsWith(NEW_LINE) ? "" : (FOUR_TABS+NEW_LINE)) +
            "\n"
    }
}


def targetDir = args[0]
//def targetDir = "c:/test/"
// TODO check null & dir exist

def dir=new File(targetDir)

//Pattern for groovy script
def vmExt = ".vm"

dir.eachFileRecurse({f->
    if (!f.getName().endsWith(".vm")){
        println "~~~~~~~~~~~~ Ignored file :" + f.getName()
        return
    }
	println "~~~~~~~~~~~~ Start process file :" + f.getName()
	
    def targetText = ""
    def depth=0
    
    f.eachLine({line->
    
      if (""==line.trim()){
          // println "blank line"
          targetText += line+"\n"
          return;
      }
      def tags = getTags(line)
      println "getTags() -->" + tags

      if(tags.size()==1){
          def tag=tags[0]
          //println tag
          if (isStartsTag(tag)){
              depth++
              targetText += appendIndent(depth, line)
              println "start "+tag+" "+depth
          } else if (isMidTag(line)){
              println "mid "+tag+" "+depth
              targetText += appendIndent(depth, line)
          } else if (isEndTag(line)) {
              targetText += appendIndent(depth, line)
              depth--
              println "end "+tag+" "+depth
          }
          if (depth<0){
              throw new RuntimeException("Depth NG")
          }
          
      }else{
          //Ignore multiple tags, like if...else... in same line.
          targetText += line+"\n"
      }
    })

    //output it
    f.setText(targetText)
})