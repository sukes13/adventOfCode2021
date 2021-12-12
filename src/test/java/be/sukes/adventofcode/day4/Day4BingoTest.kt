package be.sukes.adventofcode.day4

import be.sukes.adventofcode.day4.BingoCard.CardNumber
import be.sukes.adventofcode.day4.BingoCard.CardRow
import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day4BingoTest {

    @Test
    fun `create BingoCards - matching rows for first card`() {
        val cardsString = FileReader().readLines("/day4/testCards.txt")

        val actual = parseCards(cardsString)

        assertThat(actual).hasSize(3)
        assertThat(actual[0].rows).contains(CardRow(listOf("22","13","17","11","0")),
                                         CardRow(listOf("8","2","23","4","24")),
                                         CardRow(listOf("21","9","14","16","7")),
                                         CardRow(listOf("6","10","3","18","5")),
                                         CardRow(listOf("1","12","20","15","19"))
        )

    }

    @Test
    fun `draw 8 - 8 is marked, 23 is not`() {
        val cardsString = FileReader().readLines("/day4/testCards.txt")

        val actual = parseCards(cardsString)[0].draw("8")

        assertThat(actual.rows[1].numbers).contains(CardNumber(8,true),
                                                    CardNumber(23,false))
    }

    @Test
    fun `draw 8 - check if Bingo - no bingo`() {
        val cardsString = FileReader().readLines("/day4/testCards.txt")

        val actual = parseCards(cardsString)[0].draw("8")

        assertThat(actual.isBingo()).isFalse
    }

    @Test
    fun `draw "22","13","17","11","0" - check if Bingo - bingo`() {
        val cardsString = FileReader().readLines("/day4/testCards.txt")

        val actual = parseCards(cardsString)[0].drawList(listOf("22","13","17","11","0"))

        assertThat(actual.isBingo()).isTrue
    }

    private fun parseCards(cardsString: List<String>): List<BingoCard> =
            cardsString.filter { it.isNotBlank() }
                    .windowed(5,5)
                    .map { BingoCard(it) }
}