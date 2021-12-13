package be.sukes.adventofcode.day4



class BingoCard(cardString: List<String>) {
    val rows: List<ScorableLine> = toCardRowList(cardString)

    fun draw(inputNumber: Int): Boolean {
        this.rows.forEach{ row ->
            row.markIfPresent(inputNumber)
        }
        return this.isBingo()
    }

    private fun isBingo() =
            rows.isBingo() or rows.toColumns().isBingo()

    private fun toCardRowList(cardString: List<String>) =
        cardString.map { line -> ScorableLine(line.split("  "," ")
                                                 .filter { it.isNotBlank() }
                                                 .map { it.toInt().toCardNumber() } ) }

    private fun List<ScorableLine>.toColumns(): List<ScorableLine>  {
        val columns : ArrayList<ScorableLine> = arrayListOf()
        var i = 0
        while(i < this.size){
            columns.add(ScorableLine(this.map { it.numbers[i] }))
            i++
        }
        return columns
    }

    fun unMarkedScore() : Int =
        rows.flatMap { it.numbers }
                .filter { !it.marked }
                .map { it.value }
            .sum()


    private fun List<ScorableLine>.isBingo(): Boolean =
            this.map { it.allMarked() }.any { it }

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





