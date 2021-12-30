package be.sukes.adventofcode.day14

import java.util.*


class PolymerTemplate(input: List<String>) {
    val startPolymer = input.polymer()
    val inserts = input.inserts()

    fun solutionOne(times: Int): Long {
        val endCount = insert(times).letterCount
        return endCount.values.max()!! - endCount.values.min()!!
    }

    fun insert(times: Int): Polymer {
        val start = Date()
        return (1..times).fold(startPolymer) { polymer, t ->
            println("Turn $t took  ${Date().time - start.time}")
            polymer.insert()
        }
    }

    private fun Polymer.insert(): Polymer {
        val newPolymer = copy(pairs = mutableMapOf())
        this.pairs.forEach { (pair, count) ->
            val insertLetter = inserts.getValue(pair)
            newPolymer.addNewPairs(pair, insertLetter, count)
            newPolymer.addToLetterCount(insertLetter, count)
        }
        return newPolymer
    }
}

data class Polymer(val pairs: MutableMap<Pair<Char, Char>, Long>, val letterCount: MutableMap<Char, Long>) {

    fun addNewPairs(pair: Pair<Char, Char>, insertLetter: Char, count: Long) {
        listOf(pair.first to insertLetter, insertLetter to pair.second).forEach {
            pairs[it] = if(it in pairs.keys) pairs[it]!! + count else count
        }
    }

    fun addToLetterCount(letter: Char, count: Long) {
        letterCount[letter] = if (letter in letterCount) letterCount[letter]!! + count else count
    }
}

private fun List<String>.polymer() = Polymer(toPairs(), toLetterCount())

private fun List<String>.toLetterCount() =
        this.first()
                .groupingBy { it }
                .eachCount()
                .mapValues { it.value.toLong() }
                .toMutableMap()

private fun List<String>.toPairs() =
        this.first()
                .windowed(2, 1)
                .groupingBy { it }
                .eachCount()
                .map { (x, y) ->
                    (x.first() to x.last()) to y.toLong()
                }.toMap().toMutableMap()

private fun List<String>.inserts() =
        this.filter { it.contains(" -> ") }
                .map { it.split(" -> ") }
                .map { (pair, insert) ->
                    (pair.first() to pair.last()) to insert.single()
                }.toMap()
