package be.sukes.adventofcode.day21

class DiceGame(startPositions: List<Int>) {
    val players = startPositions.toPlayers()
    val dice = Dice(1)

    fun solutionOne() =
            play(GameTurn(players)).players.single { it.isWinner().not() }
                                           .score * (dice.current - 1)

    fun play(gameTurn: GameTurn): GameTurn {
        val nextTurn = gameTurn.next(dice.roll())
        return when {
            nextTurn.hasWinner() -> return nextTurn
            else -> play(nextTurn)
        }
    }

    private fun List<Int>.toPlayers() = this.map { Player(it) }.toMutableList()

}

fun nextSpot(pos: Int, move: Int): Int {
    val sum = (pos + move)
    return when {
        sum >= 11 -> if (sum % 10 == 0) 10 else sum % 10
        else -> sum
    }
}

private fun Player.move(spots: Int): Player {
    val moveTo = nextSpot(this.position, spots)
    return this.copy(position = moveTo, score = score + moveTo)
}

data class Dice(var current: Int) {
    fun roll(): Int {
        val sumOf3 = (current..current + 2).sum()
        current += 3
        return sumOf3
    }
}

data class Player(val position: Int, val score: Int = 0) {
    fun isWinner() = score >= 1000
}

data class GameTurn(val players: MutableList<Player>,val currentPlayer:Int = 0) {
    fun next(roll: Int): GameTurn {
        players[currentPlayer] = players[currentPlayer].move(roll)
        return this.copy( currentPlayer = currentPlayer.next())
    }
    fun hasWinner() = players.any { it.isWinner() }

    private fun Int.next() = if (this == 0) 1 else 0
}
