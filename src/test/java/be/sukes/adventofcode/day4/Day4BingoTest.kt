package be.sukes.adventofcode.day4

import be.sukes.adventofcode.day4.BingoCard.CardRow
import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day4BingoTest {

    @Test
    fun `create BingoCard - matching rows`() {
        val cardString = FileReader().readLines("/day4/testCards.txt").take(5)

        val actual = BingoCard(cardString)

        assertThat(actual.rows).contains(CardRow(listOf("22","13","17","11","0")),
                                         CardRow(listOf("8","2","23","4","24")),
                                         CardRow(listOf("21","9","14","16","7")),
                                         CardRow(listOf("6","10","3","18","5")),
                                         CardRow(listOf("1","12","20","15","19"))
        )

    }
}