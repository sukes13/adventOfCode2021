package be.sukes.adventofcode.day21

private val OUTCOMES = listOf(3 to 1, 4 to 3, 5 to 6, 6 to 7, 7 to 6, 8 to 3, 9 to 1)

class DiceGame(startPositions: List<Int>) {
    val players = startPositions.toPlayers()
    val dice = Dice(1)

    fun solutionOne(): Int {
        val initialTurn = GameTurn(players[0], players[1])
        return play(initialTurn).loser().score * (dice.current - 1)
    }

    fun solutionTwo(): Long {
        val initialTurn = GameTurn(players[0].copy(winScore = 21), players[1].copy(winScore = 21))
        return playQuantum(initialTurn).mostWins()
    }

    fun play(gameTurn: GameTurn): GameTurn =
            when {
                gameTurn.hasWinner() -> gameTurn
                else -> play(gameTurn.next(dice.roll()))
            }

    fun playQuantum(gameTurn: GameTurn): Winners =
            when {
                gameTurn.hasWinner() -> if (gameTurn.player1.isWinner()) Winners(1, 0) else Winners(0, 1)
                else -> OUTCOMES.map { (outcome, density) ->
                                    playQuantum(gameTurn.next(outcome)).addForDensity(density)
                                }.reduce { w, a -> w.addTurns(a) }
            }

    private fun List<Int>.toPlayers() = this.map { Player(it) }
}

data class Dice(var current: Int) {
    fun roll() = (current..current + 2).sum().also { current += 3 }
}

data class Player(val position: Int, val score: Int = 0, val winScore: Int = 1000) {
    fun isWinner() = score >= winScore
}

data class GameTurn(val player1: Player, val player2: Player, val currentPlayer: Int = 0) {
    fun next(roll: Int) =
            when (currentPlayer) {
                0 -> copy(player1 = player1.move(roll), player2 = player2, currentPlayer = 1)
                else -> copy(player1 = player1, player2 = player2.move(roll), currentPlayer = 0)
            }

    fun hasWinner() = player1.isWinner() || player2.isWinner()

    fun loser() = if (player1.isWinner()) player2 else player1
}

data class Winners(val player1: Long = 0L, val player2: Long = 0L) {
    fun addForDensity(density: Int) =
            copy(player1 = player1 * density, player2 = player2 * density)

    fun addTurns(turn: Winners) =
            copy(player1 = player1 + turn.player1, player2 = player2 + turn.player2)

    fun mostWins() =
            listOf(player1, player2).max()!!
}

private fun Player.move(spots: Int): Player {
    val moveTo = nextSpot(this.position, spots)
    return this.copy(position = moveTo, score = score + moveTo)
}

private fun nextSpot(pos: Int, move: Int): Int {
    val sum = (pos + move)
    return when {
        sum >= 11 -> if (sum % 10 == 0) 10 else sum % 10
        else -> sum
    }
}
