package be.sukes.adventofcode.day21

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day21DiceTest {

    @Test
    fun `create Game - players at start position`() {
        val actual = DiceGame(listOf(4, 8))

        assertThat(actual.players).containsExactly(Player(4,0),Player(8,0))
    }

    @Test
    fun `play - player 1 win with 1000 on position 10 on roll 993`() {
        val diceGame = DiceGame(listOf(4, 8))
        val actual = diceGame.play(GameTurn(diceGame.players[0],diceGame.players[1]))

        assertThat(diceGame.dice.current).isEqualTo(994)
        assertThat(actual.player1).isEqualTo(Player(10,1000))
        assertThat(actual.player2).isEqualTo(Player(3,745))
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

    @Test
    fun `test - playQuantum - correct results`() {
        val diceGame = DiceGame(listOf(4, 8))
        val actual = diceGame.playQuantum(GameTurn(diceGame.players[0].copy(winScore = 21),diceGame.players[1].copy(winScore = 21)))

        assertThat(actual.player1).isEqualTo(444356092776315L)
        assertThat(actual.player2).isEqualTo(341960390180808L)
        assertThat(actual.mostWins()).isEqualTo(444356092776315L)
    }

    @Test
    fun `test - solutionTwo - 444356092776315L`() {
        val diceGame = DiceGame(listOf(4, 8))
        val actual = diceGame.solutionTwo()

        assertThat(actual).isEqualTo(444356092776315L)
    }

    @Test
    fun `solutionTwo - correct`() {
        val diceGame = DiceGame(listOf(10, 9))
        val actual = diceGame.solutionTwo()

        assertThat(actual).isEqualTo(158631174219251L)
    }
}