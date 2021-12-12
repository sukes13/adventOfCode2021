package be.sukes.adventofcode.day4

class BingoCard(cardString: List<String>) {

    val rows : List<CardRow>  = toCardRowList(cardString)

    fun draw(inputNumber: String): BingoCard {
        this.rows.forEach { row ->
            row.numbers.forEach {number ->
                if (number.value == inputNumber.toInt())
                    number.mark()
            }
        }
        return this
    }

    private fun toCardRowList(cardString: List<String>): List<CardRow> {
        return cardString
                .map { line -> CardRow(line.split("  "," ")
                                            .filter { it.isNotBlank() })}
    }

    fun isBingo(): Boolean =
        rows.map { it.allMarked() }.filter { true }.first()

    fun drawList(drawList: List<String>): BingoCard {
        drawList.forEach { this.draw(it) }
        return this
    }


    class CardRow(rowStrings: List<String> ) {
        val numbers = toCardNumberList(rowStrings)

        private fun toCardNumberList(rowString : List<String>) =
                rowString.map { CardNumber(it.toInt()) }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as CardRow

            if (numbers != other.numbers) return false

            return true
        }

        override fun hashCode(): Int {
            return numbers.hashCode()
        }

        fun allMarked() =
                numbers.filter { it.marked }.size == numbers.size

    }

    data class CardNumber(val value: Int, var marked: Boolean = false) {
        fun mark() {
            marked = true
        }
    }
}


