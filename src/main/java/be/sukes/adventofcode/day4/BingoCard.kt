package be.sukes.adventofcode.day4

import kotlin.random.Random.Default.nextInt


class BingoCard(cardString: List<String>) {
    val id : Int = nextInt()
    val rows: List<ScorableLine> = cardString.toCardRowList()

    fun draw(inputNumber: Int): Boolean {
        this.rows.forEach{ row ->
            row.markIfPresent(inputNumber)
        }
        return this.isBingo()
    }

    private fun isBingo() =
            rows.isBingo() or rows.toColumns().isBingo()

    fun unMarkedScore() : Int =
        rows.flatMap { it.numbers }
                .filter { !it.marked }
                .map { it.value }
                .sum()

    private fun List<ScorableLine>.toColumns(): List<ScorableLine>  {
        val columns : ArrayList<ScorableLine> = arrayListOf()
        var i = 0
        while(i < this.size){
            columns.add(ScorableLine(this.map { it.numbers[i] }))
            i++
        }
        return columns
    }


    private fun List<ScorableLine>.isBingo(): Boolean =
            this.map { line -> line.allMarked() }
                .any { it }

    private fun List<String>.toCardRowList(): List<ScorableLine> =
            this.map { line -> ScorableLine(line.split("  "," ")
                                                .filter { it.isNotBlank() }
                                                .map { it.toInt().toCardNumber() } ) }

}

data class ScorableLine(val numbers: List<CardNumber> ) {
    fun markIfPresent( inputNumber: Int) {
        numbers.forEach {number ->
            if (number.value == inputNumber)
                number.mark()
        }
    }

    fun allMarked() =
            numbers.filter { it.marked }.size == numbers.size
}

fun Int.toCardNumber() : CardNumber = CardNumber(this)

data class CardNumber(val value: Int, var marked: Boolean = false) {
    fun mark() {
        marked = true
    }
}





