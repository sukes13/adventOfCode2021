package be.sukes.adventofcode.day14

class PolymerTemplate(input: List<String>) {
    val startPolymer = input.polymer()
    val inserts: List<Insert> = input.inserts()

    fun solutionOne(times: Int): Int {
        val count = insert(times).letterCount()
        return count.values.max()!! - count.values.min()!!
    }

    fun insert(times: Int) =
            (1..times).fold(startPolymer) { polymer, _ ->
                polymer.insert()
            }

    private fun List<String>.insert() =
            this.windowed(2, 1)
                    .map { (x, y) -> inserted(x, y) }
                    .mapIndexed { index, list ->
                        if (index > 0) list.removeAt(0)
                        list
                    }.flatten()

    private fun inserted(x: String, y: String) =
            listOf(x, inserts.single { it.pair == x to y }.letter, y).toMutableList()

    private fun List<String>.letterCount() = this.groupingBy { it }.eachCount()
}

data class Insert(val pair: Pair<String, String>, val letter: String)

private fun List<String>.polymer() = this.first().trim().toList().map { "$it" }

private fun List<String>.inserts() =
        this.filter { it.contains(" -> ") }
                .map { it.split(" -> ") }
                .map { (pair, insert) ->
                    Insert(pair.first().toString() to pair.last().toString(), insert.single().toString())
                }
