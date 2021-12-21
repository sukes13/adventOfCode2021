package be.sukes.adventofcode.day10

import be.sukes.adventofcode.day10.NavSystem.CharType.*
import be.sukes.adventofcode.day10.NavSystem.CharType.Companion.isCloserOf
import be.sukes.adventofcode.day10.NavSystem.CharType.Companion.isOpenerOf


class NavSystem {

    fun solutionOne(input: List<String>) =
            input.map { corruptingCharOf(it).isCloserOf().corruptScore }
                 .sum()

    fun solutionTwo(input: List<String>) =
            input.map { completionOf(it) }
                 .map { completionScoreOf(it) }
                 .filterNot { it==0L }
                 .getMiddle()

    fun corruptingCharOf(line: String) =
            line.removeValidChunks()
                    .firstOrNull{ it.toString() in closingChars() }
                    ?.toString() ?: ""

    fun completionOf(line: String): String {
        val inValidChars = line.removeValidChunks()
        return when {
            corruptingCharOf(inValidChars).isNotBlank() -> ""
            else -> inValidChars.map { it.toString().isOpenerOf().closer }
                                .reversed()
                                .joinToString("")
        }
    }

    fun completionScoreOf(line: String) =
            line.toList().fold(0L){ score, char ->
                            (score * 5L) + char.toString().isCloserOf().completeScore
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

    private fun List<Long>.getMiddle() = this.sorted()[(this.size - 1) / 2]
    private fun closingChars() = values().map { it.closer }

    enum class CharType(val opener: String, val closer : String, val corruptScore:Int, val completeScore: Int) {
        PARENTHESIS("(",")",3,1),
        BRACKETS("[","]",57,2),
        BRACES("{","}",1197,3),
        DIAMONDS("<",">",25137,4),
        EMPTY("","",0,0);

        val chunk : String = opener + closer
        companion object {
            fun String.isOpenerOf() = values().find { value -> value.opener == this } ?: EMPTY
            fun String.isCloserOf() = values().find { value -> value.closer == this } ?: EMPTY
        }
    }

}
