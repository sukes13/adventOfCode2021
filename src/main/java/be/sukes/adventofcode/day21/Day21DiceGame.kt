package be.sukes.adventofcode.day21

class DiceGame(startPositions: List<Int>) {
    val players = startPositions.toPlayers()
    val dice = Dice(1)

    fun solutionOne() = play(0).players.single { it.isWinner().not() }
                                                  .score * (dice.current - 1)

    fun play(current:Int): DiceGame {
        players[current] = players[current].move(dice.roll())
        if (players[current].isWinner().not()) {
            play(current.next())
        }
        return this
    }

    private fun Int.next() = if (this == 0) 1 else 0

    private fun Player.move(spots: Int): Player {
        val moveTo = nextSpot(this.position, spots)
        return this.copy(position = moveTo, score = score + moveTo)
    }

    fun nextSpot(pos: Int, move: Int): Int {
        val sum = (pos + move)
        return when {
            sum >= 11 -> if (sum % 10 == 0) 10 else sum % 10
            else -> sum
        }
    }

    private fun List<Int>.toPlayers() = this.map { Player(it) }.toMutableList()

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
