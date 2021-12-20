package be.sukes.adventofcode.day10

class NavSystem {

    fun isCorruptLine(input: String) : String {
        val invalids = filterValids(input)

        return when {
            ")" in invalids -> ")"
            "]" in invalids -> "}"
            "}" in invalids -> "}"
            ">" in invalids -> ">"
            else -> ""
        }
    }


    private tailrec fun filterValids(line : String) : String{
        val filtered = line.replace("()","")
                                 .replace("{}","")
                                 .replace("[]","")
                                 .replace("<>","")
        if (filtered.length == line.length){
            return line
        }
        return filterValids(filtered)
    }
}
