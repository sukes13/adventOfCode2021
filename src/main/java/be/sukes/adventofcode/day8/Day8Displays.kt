package be.sukes.adventofcode.day8

fun List<String>.solutionOne() =
        this.map { Display(it).detectEasyOutputDigits().size }
                .sum()

class Display(entryString: String) {
    private val entries = entryString.toEntries()
    val inDigits = entries.first
    val outDigits = entries.second

    fun detectEasyOutputDigits(): List<Signal> {
        return outDigits.mapNotNull {
            when (it.length) {
                2 -> 1 to it
                3 -> 7 to it
                4 -> 4 to it
                7 -> 8 to it
                else -> null
            }
        }
    }

    private fun String.toEntries() =
            this.split(" | ")
                    .windowed(2)
                    .map { (sig,dis) ->
                        sig.toStringList() to dis.toStringList()
                    }.single()

    private fun String.toStringList() =
            this.split(" ")

}

typealias Signal = Pair<Int,String>
