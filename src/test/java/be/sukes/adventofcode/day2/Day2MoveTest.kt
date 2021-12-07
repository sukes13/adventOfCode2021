package be.sukes.adventofcode.day2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day2MoveTest{

    @Test
    internal fun `start position - do move 'forward 2' - position is 2, depth is 0`() {
        val actual : Submarine = Submarine().doMove("forward 2")

        assertThat(actual).isEqualTo(Submarine(2,0))
    }

    @Test
    internal fun `start position - do move 'up 2' - position is 0, depth is 2`() {
        val actual : Submarine = Submarine().doMove("up 2")

        assertThat(actual).isEqualTo(Submarine(0,2))
    }

    @Test
    fun `test move - getHorizontal - is 15`() {
//        val moves : List<String> = FileReader().readLines("/day2/testMoves.txt")
//
//        val actual : Int = Day2Move().doMoves(moves)
//
//        assertThat(actual).isEqualTo(15)
    }
}