package be.sukes.adventofcode.day21

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day21DiceTest {

    @Test
    fun `create Game - players at start position`() {
        val actual = DiceGame(listOf(4, 8))

        assertThat(actual.players).containsExactly(Player(4,0),Player(8,0))
    }

//    @Test
//    fun `roll Dice - players at start position`() {
//        val actual = Dice(1)
//         actual.roll()
//         actual.roll()
//         actual.roll()
//         actual.roll()
//         actual.roll()
//         val n = actual.roll()
//
//        assertThat(n).isEqualTo(51)
//        assertThat(actual.current).isEqualTo(18)
//    }

    @Test
    fun `play - player 1 win with 1000 on position 10 on roll 993`() {
        val diceGame = DiceGame(listOf(4, 8))
        val actual = diceGame.play(0)

        assertThat(actual.dice.current).isEqualTo(994)
        assertThat(actual.players).containsExactly(Player(10,1000),Player(3,745))
    }

    @Test
    fun `test solutionOne - 739785`() {
        val diceGame = DiceGame(listOf(4, 8))
        val actual = diceGame.solutionOne()

        assertThat(actual).isEqualTo(739785)
    }

    @Test
    fun `solutionOne - `() {
        val diceGame = DiceGame(listOf(10, 9))
        val actual = diceGame.solutionOne()

        assertThat(actual).isEqualTo(918081)
    }

}