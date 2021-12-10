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

    fun doMove(move: String): Submarine = move.asCommand().execute(this)

    internal fun moveHorizontal(value: Int): Submarine = copy(position = position.plus(value))
    internal fun moveVertical(value: Int): Submarine = copy(depth = depth + aim.times(value))
    internal fun aimUp(value: Int): Submarine = copy(aim = aim.minus(value))
    internal fun aimDown(value: Int): Submarine = copy(aim = aim.plus(value))

    fun solution(): Int = position * depth
}

interface MoveCommand{
    fun execute(submarine: Submarine) : Submarine
}
class MoveForward(val value: Int) : MoveCommand {
    override fun execute(submarine: Submarine) : Submarine =
            submarine.moveHorizontal(value)
                     .moveVertical(value)
}
class AimUp(val value: Int) : MoveCommand {
    override fun execute(submarine: Submarine) : Submarine =
            submarine.aimUp(value)
}
class AimDown(val value: Int) : MoveCommand {
    override fun execute(submarine: Submarine) : Submarine =
            submarine.aimDown(value)
}

fun String.asCommand(): MoveCommand {
    val (command,value) = this.trim().split(" ")
            .windowed(2)
            .map {(comm,value) -> comm to value.toInt()}
            .first()
    when (command) {
        "forward" -> return MoveForward(value)
        "up" -> return AimUp(value)
        "down" -> return AimDown(value)
    }
    throw IllegalMoveException()
}

class IllegalMoveException : Throwable()

