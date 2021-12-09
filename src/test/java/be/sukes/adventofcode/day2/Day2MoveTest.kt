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
    fun `start position - do move 'up 2' - position is 0, depth is -2`() {
        val actual : Submarine = Submarine().doMove("up 2")

        assertThat(actual).isEqualTo(Submarine(0, -2, 0))
    }

    @Test
    fun `start position - do move 'down 2' - position is 0, depth is 2`() {
        val actual : Submarine = Submarine().doMove("down 2")

        assertThat(actual).isEqualTo(Submarine(0, 2, 0))
    }

    @Test
    fun `start position - do moves 'down 2', 'up 5' - position is 0, depth is -3`() {
        val actual : Submarine = Submarine().doMove("down 2")
                                            .doMove("up 5")

        assertThat(actual).isEqualTo(Submarine(0, -3, 0))
    }

    @Test
    fun `test moves - position is 15, depth is 10`() {
        val moves : List<String> = FileReader().readLines("/day2/testMoves.txt")

        val actual : Submarine = Day2Move().doMoves(moves)

        assertThat(actual).isEqualTo(Submarine(15, 10, 0))
    }

    @Test
    fun `moves - position is 1944, depth is 1049`() {
        val moves : List<String> = FileReader().readLines("/day2/moves.txt")

        val actual : Submarine = Day2Move().doMoves(moves)

        assertThat(actual).isEqualTo(Submarine(1944, 1049, 0))
    }

    @Test
    fun `moves - solution = 2039256`() {
        val moves : List<String> = FileReader().readLines("/day2/moves.txt")

        val actual : Int = Day2Move().doMoves(moves).solution()

        assertThat(actual).isEqualTo(2039256)
    }
}