package be.sukes.adventofcode.day2

data class Submarine(val position: Int = 0,
                     val depth: Int = 0,
                     val aim: Int = 0){

    fun doMoves(moves: List<String>): Submarine =
            moves.fold(Submarine()) {
                submarine , move -> submarine.doMove(move)
            }

    fun doMove(move: String): Submarine {
        when(val command = move.asCommand()){
            is MoveForward -> return this.moveHorizontal(command.value)
                                         .moveVertical(command.value)
            is AimUp -> return this.aimUp(command.value)
            is AimDown -> return this.aimDown(command.value)
        }
        throw IllegalMoveException()
    }
    private fun moveHorizontal(value: Int): Submarine =
            copy(position = position.plus(value))
    private fun moveVertical(value: Int): Submarine =
            copy(depth = depth.plus(aim.times(value)))
    private fun aimUp(value: Int): Submarine =
            copy(aim = aim.minus(value))
    private fun aimDown(value: Int): Submarine =
            copy(aim = aim.plus(value))

    fun solution(): Int = position.times(depth)
}

interface MoveCommand{
    val value: Int
}
class MoveForward(override val value: Int) : MoveCommand
class AimUp(override val value: Int) : MoveCommand
class AimDown(override val value: Int) : MoveCommand

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

