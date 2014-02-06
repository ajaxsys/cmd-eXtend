def USAGE="Usage: splitfile filePath splitSize(MB)"
if (args.length<2) {
    println USAGE
    return
}

def ONE_MB_SIZE=1024*1024 // 1M
def input=new File(args[0])

def mb=args[1]

if (!input.exists() || !mb.isNumber() ) {
    println USAGE
    return
}


int SPLIT_SIZE=ONE_MB_SIZE * mb.toDouble()

def sb = new StringBuilder("")
def count = 0

input.eachLine {
    if (sb.length() < SPLIT_SIZE) {
        sb.append(it).append("\n")
        if (sb.length() >= SPLIT_SIZE) {
            count++
            new File(input.getAbsoluteFile().getParent() + File.separator + 'split_' + count + '_' + input.getName()).setText(sb.toString())
            sb.setLength(0)
        }
    }
}

if (sb.length() > 0) {
    count++
    new File(input.getAbsoluteFile().getParent() + File.separator + 'split_' + count + '_' + input.getName()).setText(sb.toString())
    sb.setLength(0)
}


println "Split OK. Split to $count files"