package be.sukes.adventofcode.day4

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day4BingoTest {

    @Test
    fun `create BingoCards - matching rows for first card`() {
        val cardsString = FileReader().readLines("/day4/testCards.txt")

        val actual = cardsString.asBingoCards()

        assertThat(actual).hasSize(3)
        assertThat(actual[0].rows).contains(ScoredLine(listOf(22.toCardNumber(),13.toCardNumber(),17.toCardNumber(),11.toCardNumber(),0.toCardNumber())),
                                             ScoredLine(listOf(8.toCardNumber(),2.toCardNumber(),23.toCardNumber(),4.toCardNumber(),24.toCardNumber())),
                                             ScoredLine(listOf(21.toCardNumber(),9.toCardNumber(),14.toCardNumber(),16.toCardNumber(),7.toCardNumber())),
                                             ScoredLine(listOf(6.toCardNumber(),10.toCardNumber(),3.toCardNumber(),18.toCardNumber(),5.toCardNumber())),
                                             ScoredLine(listOf(1.toCardNumber(),12.toCardNumber(),20.toCardNumber(),15.toCardNumber(),19.toCardNumber()))
                                             )
    }

    @Test
    fun `draw 8 - 8 is marked, 23 is not, no bingo`() {
        val cardsString = FileReader().readLines("/day4/testCards.txt")

        val bingoCard = cardsString.asBingoCards()[0]
        val actual = bingoCard.draw(8)

        assertThat(bingoCard.rows[1].numbers).contains(CardNumber(8,true),
                                                    CardNumber(23,false))
        assertThat(actual).isFalse()
    }

    @Test
    fun `draw entire row - check if Bingo - bingo`() {
        val cardsString = FileReader().readLines("/day4/testCards.txt")
        val bingoCard = cardsString.asBingoCards()[0]

        assertThat(bingoCard.draw(8)).isFalse()
        assertThat(bingoCard.draw(2)).isFalse()
        assertThat(bingoCard.draw(23)).isFalse()
        assertThat(bingoCard.draw(4)).isFalse()
        assertThat(bingoCard.draw(24)).isTrue()
    }

    @Test
    fun `draw entire column - check if Bingo - bingo`() {
        val cardsString = FileReader().readLines("/day4/testCards.txt")
        val bingoCard = cardsString.asBingoCards()[0]

        assertThat(bingoCard.draw(13)).isFalse()
        assertThat(bingoCard.draw(2)).isFalse()
        assertThat(bingoCard.draw(9)).isFalse()
        assertThat(bingoCard.draw(10)).isFalse()
        assertThat(bingoCard.draw(12)).isTrue()
    }

    @Test
    fun `test draw - score = 188`() {
        val cardsString = FileReader().readLines("/day4/testCards.txt")
        val drawsString = FileReader().readLines("/day4/testDraw.txt")

        val actual = Day4Bingo().play(cardsString,drawsString[0])

        assertThat(actual).isEqualTo(4512)
    }

    @Test
    fun `draw - score = solution`() {
        val cardsString = FileReader().readLines("/day4/cards.txt")
        val drawsString = FileReader().readLines("/day4/draws.txt")

        val actual = Day4Bingo().play(cardsString,drawsString[0])

        assertThat(actual).isEqualTo(14093)
    }
}