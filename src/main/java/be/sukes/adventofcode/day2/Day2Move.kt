package be.sukes.adventofcode.day2

class Day2Move {
    fun doMoves(moves: List<String>): Submarine {
        var submarine = Submarine()
        moves.forEach {
            submarine = submarine.doMove(it)
        }
        return submarine
    }
}

data class Submarine(val position: Int = 0,
                     val depth: Int = 0,
                     val aim: Int = 0){

    fun doMove(move: String): Submarine = parseMove(move).execute(this)

    private fun parseMove(move: String) : MoveCommand {
        val commandAsStrings = move.trim().split(" ")
        val (command,value) = commandAsStrings[0] to commandAsStrings[1].toInt()
        when (command) {
            "forward" -> return MoveForward(value)
            "up" -> return AimUp(value)
            "down" -> return AimDown(value)
        }
        throw IllegalMoveException()
    }

    interface MoveCommand{
        fun execute(submarine: Submarine) : Submarine
    }
    class MoveForward(val value: Int) : MoveCommand {
        override fun execute(submarine: Submarine) : Submarine = submarine.copy(position = submarine.position.plus(value),
                                                                                depth = submarine.depth + submarine.aim.times(value))
    }
    class AimUp(val value: Int) : MoveCommand {
        override fun execute(submarine: Submarine) : Submarine = submarine.copy(aim = submarine.aim.minus(value))
    }
    class AimDown(val value: Int) : MoveCommand {
        override fun execute(submarine: Submarine) : Submarine = submarine.copy(aim = submarine.aim.plus(value))
    }

    fun solution(): Int = position * depth
}


class IllegalMoveException : Throwable()

