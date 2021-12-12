package be.sukes.adventofcode.day4

class BingoCard(cardString: List<String>) {

    val rows : List<CardRow>  = toCardRowList(cardString)

    data class CardNumber(val value: Int, val marked: Boolean = false)

    private fun toCardRowList(cardString: List<String>): List<CardRow> {
        return cardString
                .map { line -> CardRow(line.split("  "," ")
                                            .filter { it.isNotBlank() })}
    }


    class CardRow(rowStrings: List<String> ) {
        val numbers : List<CardNumber>  = toCardNumberList(rowStrings)

        private fun toCardNumberList(rowString : List<String>): List<CardNumber> =
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


    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BingoCard

        if (rows != other.rows) return false

        return true
    }

    override fun hashCode(): Int {
        return rows.hashCode()
    }


}


