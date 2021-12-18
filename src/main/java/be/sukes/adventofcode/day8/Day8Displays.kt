package be.sukes.adventofcode.day8

fun List<String>.solutionOne() =
        this.map { Display(it).outDigits.detectEasySignals().size }
                .sum()

fun List<String>.solutionTwo() =
        this.map{ Display(it).detectSignals().show() }
                .sum()

fun List<String>.detectEasySignals() =
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
    val signals = this.inDigits.detectEasySignals().toMap().toMutableMap()

    fun detectSignals(): Display {
        val segOfFive = allSegOf(5)
        signals[3] = segOfFive.single { it.stripOfLetters(signals[1]!!).length == 3 }
        signals[5] = segOfFive.filter { it != signals[3] }
                .single { it.stripOfLetters(signals[4]!!).length == 2 }
        signals[2] = segOfFive.single { it != signals[3] && it != signals[5] }

        val segOfSix = allSegOf(6)
        signals[6] = segOfSix.single { it.stripOfLetters(signals[1]!!).length == 5 }
        signals[9] = segOfSix.filter { it != signals[6] }
                .single { it.stripOfLetters(signals[4]!!).length == 2 }
        signals[0] = segOfSix.single { it != signals[6] && it != signals[9] }

        return this
    }

    private fun allSegOf(segLength: Int) = this.inDigits.filter { it.length == segLength }

    private fun String.toEntries() =
            this.split(" | ")
                    .windowed(2)
                    .map { (sig, dis) ->
                        sig.toStringList() to dis.toStringList()
                    }.single()

    private fun String.toStringList() =
            this.split(" ")
                    .map { it }

    private fun String.stripOfLetters(stripString: String) =
            stripString.fold(this, { it, stripper ->
                return@fold it.replace(stripper.toString(), "")
            })

    fun show(): Int {
        return this.outDigits.map { out ->
            signals.filterValues { out.alphabetical() == it.alphabetical() }.keys.single()
        }.joinToString("").toInt()
    }
}

fun String.alphabetical() = this.toCharArray().sorted().joinToString("")
