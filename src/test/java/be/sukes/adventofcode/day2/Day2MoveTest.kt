package be.sukes.adventofcode.day2

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day2MoveTest{

    @Test
    fun `start position - do move 'forward 2' - position = 2, depth = 0, aim = 0`() {
        val actual : Submarine = Submarine().doMove("forward 2")

        assertThat(actual).isEqualTo(Submarine(2,0,0))
    }

    @Test
    fun `start position - do move 'up 2' - position is 0, depth is 0, aim = -2`() {
        val actual : Submarine = Submarine().doMove("up 2")

        assertThat(actual).isEqualTo(Submarine(0, 0, -2))
    }

    @Test
    fun `start position - do move 'down 2' - position is 0, depth is 0, aim = 2`() {
        val actual : Submarine = Submarine().doMove("down 2")

        assertThat(actual).isEqualTo(Submarine(0, 0, 2))
    }


    @Test
    fun `start position - do moves 'down 2', 'forward 3' - position is 0, depth is -3`() {
        val actual : Submarine = Submarine().doMove("down 2")
                                            .doMove("forward 3")

        assertThat(actual).isEqualTo(Submarine(3, 6, 2))
    }

    @Test
    fun `test moves - position is 15, depth is 10`() {
        val moves : List<String> = FileReader().readLines("/day2/testMoves.txt")

        val actual : Submarine = Submarine().doMoves(moves)

        assertThat(actual).isEqualTo(Submarine(15, 60, 10))
    }

    @Test
    fun `test moves - solution = 900`() {
        val moves : List<String> = FileReader().readLines("/day2/testMoves.txt")

        val actual : Int = Submarine().doMoves(moves).solution()

        assertThat(actual).isEqualTo(900)
    }

    @Test
    fun `moves - position is 1944, depth is 954969`() {
        val moves : List<String> = FileReader().readLines("/day2/moves.txt")

        val actual : Submarine = Submarine().doMoves(moves)

        assertThat(actual).isEqualTo(Submarine(1944, 954969, 1049))
    }

    @Test
    fun `moves - solution = 1856459736`() {
        val moves : List<String> = FileReader().readLines("/day2/moves.txt")

        val actual : Int = Submarine().doMoves(moves).solution()

        assertThat(actual).isEqualTo(1856459736)
    }
}