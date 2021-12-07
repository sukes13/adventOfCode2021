package be.sukes.adventofcode.day2

class Day2Move {
    fun doMoves(moves: List<String>): Submarine {
        return Submarine().doMove(moves.first())
    }
}

data class Submarine(val position : Int = 0, val depth : Int = 0){
    fun doMove(move: String): Submarine {
        val commandAsString = move.trim().split(" ")
        when(commandAsString[0]){
            "forward" -> return this.copy(position = position.plus(commandAsString[1].toInt()))
            "up" -> return this.copy(depth = depth.plus(commandAsString[1].toInt()))
        }
        throw IllegalMoveException()
    }

}

class IllegalMoveException : Throwable()

