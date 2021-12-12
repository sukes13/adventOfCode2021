package be.sukes.adventofcode.day4



class BingoCard(cardString: List<String>) {
    val rows: List<ScoredLine> = toCardRowList(cardString)

    fun draw(inputNumber: Int): Boolean {
        this.rows.forEach{ row ->
            row.markIfPresent(inputNumber)
        }
        return this.isBingo()
    }

    private fun isBingo() =
            rows.isBingo() or rows.toColumns().isBingo()

    private fun toCardRowList(cardString: List<String>) =
        cardString.map { line -> ScoredLine(line.split("  "," ")
                                                 .filter { it.isNotBlank() }
                                                 .map { it.toInt().toCardNumber() } ) }

    private fun List<ScoredLine>.toColumns(): List<ScoredLine>  {
        val columns : ArrayList<ScoredLine> = arrayListOf()
        var i = 0
        while(i < this.size){
            columns.add(ScoredLine(this.map { it.numbers[i] }))
            i++
        }
        return columns
    }

    fun unMarkedScore() : Int =
        rows.flatMap { it.numbers }
                .filter { !it.marked }
                .map { it.value }
            .sum()


    private fun List<ScoredLine>.isBingo(): Boolean =
            this.map { it.allMarked() }.any { it }

}

class ScoredLine(val numbers: List<CardNumber> ) {
    fun markIfPresent( inputNumber: Int) {
        numbers.forEach {number ->
            if (number.value == inputNumber)
                number.mark()
        }
    }

    fun allMarked() =
            numbers.filter { it.marked }.size == numbers.size

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScoredLine

        if (numbers != other.numbers) return false

        return true
    }

    override fun hashCode(): Int =  numbers.hashCode()
}

fun Int.toCardNumber() : CardNumber = CardNumber(this)

data class CardNumber(val value: Int, var marked: Boolean = false) {
    fun mark() {
        marked = true
    }
}





