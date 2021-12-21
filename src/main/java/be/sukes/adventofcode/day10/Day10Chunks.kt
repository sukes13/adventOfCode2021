package be.sukes.adventofcode.day10

class NavSystem {

    fun solutionOne(input: List<String>) =
            input.map { getCorruptCharacter(it).toErrorScore() }
                 .sum()

    fun getCorruptCharacter(input: String) =
            removeValidChunks(input).firstOrNull{ it.toString() in listOf(")", "}", "]", ">") }?.toString() ?: ""

    private tailrec fun removeValidChunks(line : String) : String{
        val filtered = line.replace("()","")
                                 .replace("{}","")
                                 .replace("[]","")
                                 .replace("<>","")
        if (filtered.length == line.length){
            return line
        }
        return removeValidChunks(filtered)
    }

    private fun String.toErrorScore() : Int {
        return when{
            equals(")") -> 3
            equals("]") -> 57
            equals("}") -> 1197
            equals(">") -> 25137
            else -> 0
        }
    }
//
//    fun getCompletion(s: String): String {
//        return ""
//    }

}
