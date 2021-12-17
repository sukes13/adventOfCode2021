package be.sukes.adventofcode.day8

fun List<String>.solutionOne() =
        this.map { Display(it).outDigits.detectEasySignals().size }
                .sum()

fun List<String>.solutionTwo() =
        0

fun List<String>.detectEasySignals(): List<Pair<Int,String>> =
        this.mapNotNull {
            when (it.length) {
                2 -> 1 to it
                3 -> 7 to it
                4 -> 4 to it
                7 -> 8 to it
                else -> null
            }
        }

class Display(entryString: String) {
    private val entries = entryString.toEntries()
    val inDigits = entries.first
    val outDigits = entries.second

    fun detectSignals(): Map<Int,String> {
        val solved = this.inDigits.detectEasySignals().toMap().toMutableMap()
        var unsolved= this.inDigits.filterNot { solved.containsValue(it) }

        solved[3] = unsolved.filter { it.length == 5 }.single { it.stripOfLetters(solved[1]!!).length  == 3 }
        unsolved= unsolved.filterNot { it == solved[3] }

        solved[5] = unsolved.filter { it.length == 5 }.single { it.stripOfLetters(solved[4]!!).length  == 2 }

        solved[2] = unsolved.single { it != solved[5] && it.length == 5 }

        solved[6] = unsolved.filter { it.length == 6 }.single { it.stripOfLetters(solved[1]!!).length  == 5 }
        unsolved = unsolved.filterNot { it == solved[6] }

        solved[9] = unsolved.filter { it.length == 6 }.single { it.stripOfLetters(solved[4]!!).length == 2 }
        solved[0] = unsolved.single { it != solved[9] && it.length == 6 }

        return solved
    }

    private fun String.toEntries() =
            this.split(" | ")
                    .windowed(2)
                    .map { (sig,dis) ->
                        sig.toStringList() to dis.toStringList()
                    }.single()

    private fun String.toStringList() =
            this.split(" ")
                    .map { it }

    private fun String.stripOfLetters(stripString : String) =
            stripString.fold(this, {it , stripper ->
                return@fold it.replace(stripper.toString(),"")
            })

}


//
//fun List<String>.detectEasySignalsd(): Map<Int,String> =
//    this.toList().fold(mutableMapOf() , { signals, seg ->
//        when (seg.length) {
//            2 -> signals[1] = seg
//            3 -> signals[7] = seg
//            4 -> signals[4] = seg
//            7 -> signals[8] = seg
//            else -> null
//        }
//        return@fold signals
//    })