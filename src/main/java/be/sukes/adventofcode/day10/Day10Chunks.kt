package be.sukes.adventofcode.day10

import be.sukes.adventofcode.day10.NavSystem.CharType.*
import be.sukes.adventofcode.day10.NavSystem.CharType.Companion.closesWith
import be.sukes.adventofcode.day10.NavSystem.CharType.Companion.opensWith


class NavSystem {

    fun solutionOne(input: List<String>) =
            input.map { corruptingCharOf(it).toErrorScore() }
                 .sum()

    fun corruptingCharOf(line: String) =
            line.removeValidChunks()
                .firstOrNull{ it.toString() in closingChars() }
                ?.toString() ?: ""

    fun getCompletion(line: String): String {
        val inValidChars = line.removeValidChunks()
        return when {
            corruptingCharOf(inValidChars).isNotBlank() -> ""
            else -> inValidChars.map { it.toString().closedBy() }
                                .reversed()
                                .joinToString("")
        }
    }

    private tailrec fun String.removeValidChunks() : String{
        val filtered = this.replace(PARENTHESIS.chunk,"")
                                 .replace(BRACKETS.chunk,"")
                                 .replace(BRACES.chunk,"")
                                 .replace(DIAMONDS.chunk,"")
        if (filtered.length == this.length){
            return this
        }
        return filtered.removeValidChunks()
    }

    private fun String.toErrorScore() = closesWith(this).corruptScore

    private fun String.closedBy() = opensWith(this).close

    private fun closingChars() = values().map { it.close }

    enum class CharType(val open: String, val close : String, val corruptScore:Int, val completeScore: Int) {
        PARENTHESIS("(",")",3,1),
        BRACKETS("[","]",57,2),
        BRACES("{","}",1197,3),
        DIAMONDS("<",">",25137,4),
        DEFAULT("","",0,0);

        val chunk : String = open + close
        companion object {
            fun opensWith(char : String) : CharType{
                return  values().find { value -> value.open == char } ?: DEFAULT
            }
            fun closesWith(char : String) : CharType{
                return  values().find { value -> value.close == char } ?: DEFAULT
            }
        }
    }

}
