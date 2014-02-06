def goovySrcFile=args[0]

def targetDir=goovySrcFile+"_classes"
def targetJar=goovySrcFile+".jar"
def conf=new org.codehaus.groovy.control.CompilerConfiguration()
conf.setTargetDirectory(targetDir)

def c = new org.codehaus.groovy.tools.Compiler(conf) 
c.compile(goovySrcFile)

def mainClass=goovySrcFile.replace('.groovy','')
// jar cfe XXX.jar foo.Main -C CustomizeDir CustomizeDir\*.*
// Notice: "jar cfe " need the same option order

def cmd="cmd /C cd ${targetDir} & jar cfe ../${targetJar} ${mainClass} *.*"

println cmd
println cmd.execute().text
new File(targetDir).deleteDir()

println "============================="
println "${targetJar} is created. "
println "You can call it from java:"
println """   java -jar "${targetJar}" params...    """
println ""
println "NOTICE: With 'java -jar', the -classpath argument is ignored."
println "If your code used groovy lib, set groovy to your class path:"
println """   java -cp "file/to/groovy-all.jar;${targetJar}" ${mainClass} params...    """

println "============================="