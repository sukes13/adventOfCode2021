package be.sukes.adventofcode.day2

class Day2Move {
    fun doMoves(moves: List<String>): Submarine {
        var submarine = Submarine(aim = 0)
        for (move in moves)
            submarine = submarine.doMove(move)

        return submarine
    }
}

data class Submarine(val position: Int = 0,
                     val depth: Int = 0,
                     val aim: Int = 0){

    fun doMove(move: String): Submarine {
        val moveCommand : MoveCommand = parseMove(move)
        when(moveCommand){
            is MoveForward -> return this.copy(position = position.plus(moveCommand.value))
            is AimUp -> return this.copy(depth = depth.minus(moveCommand.value))
            is AimDown -> return this.copy(depth = depth.plus(moveCommand.value))
        }

        throw IllegalMoveException()
    }

    private fun parseMove(move: String) : MoveCommand {
        val commandAsStrings = move.trim().split(" ")
        val (command,value) = Pair(commandAsStrings[0],commandAsStrings[1].toInt())
        when (command) {
            "forward" -> return MoveForward(value)
            "up" -> return AimUp(value)
            "down" -> return AimDown(value)
        }
        throw IllegalMoveException()
    }

    open class MoveCommand(val value : Int)
    class MoveForward(value: Int) : MoveCommand(value)
    class AimUp(value: Int) : MoveCommand(value)
    class AimDown(value: Int) : MoveCommand(value)


    fun solution(): Int {
        return position * depth
    }

}


class IllegalMoveException : Throwable()

