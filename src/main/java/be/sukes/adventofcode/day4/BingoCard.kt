package be.sukes.adventofcode.day4



class BingoCard(cardString: List<String>) {
    val rows : List<ScoredLine>  = toCardRowList(cardString)

    fun drawList(drawList: List<String>): BingoCard {
        drawList.forEach { this.draw(it) }
        return this
    }

    fun isBingo() =
            rows.isBingo() or rows.toColumns().isBingo()

    private fun draw(inputNumber: String) =
        this.rows.forEach { row ->
            row.markIfPresent(inputNumber)
        }

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

}

private fun List<ScoredLine>.isBingo(): Boolean =
        this.map { it.allMarked() }.any { it }

class ScoredLine(val numbers: List<CardNumber> ) {
    fun markIfPresent( inputNumber: String) {
        numbers.forEach {number ->
            if (number.value == inputNumber.toInt())
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





