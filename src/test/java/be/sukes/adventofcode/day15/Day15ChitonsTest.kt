package be.sukes.adventofcode.day15

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day15ChitonsTest {
    @Test
    fun `create ChitonNav`() {
        val chitonNav = ChitonNav(FileReader().readLines("/day15/testChitons.txt"))

        assertThat(chitonNav.caveSpots).hasSize(100)
    }

    @Test
    fun `test chitons - solutionOne - 40`() {
        val chitonNav = ChitonNav(FileReader().readLines("/day15/testChitons.txt"))

        val actual = chitonNav.solutionOne(Spot(0,0), Spot(9,9))

        assertThat(actual).isEqualTo(40)
    }

    @Test
    fun `chitons - solutionOne - 487`() {
        val chitonNav = ChitonNav(FileReader().readLines("/day15/chitons.txt"))

        val actual = chitonNav.solutionOne(Spot(0,0), Spot(99,99))

        assertThat(actual).isEqualTo(487)
    }

    @Test
    fun `test chitons - toTotalCave - is correct`() {
        val chitonNav = ChitonNav(FileReader().readLines("/day15/testChitons.txt"))

        val actual = chitonNav.caveSpots.toTotalCave()

        assertThat(actual).containsExactlyInAnyOrder(*ChitonNav(FileReader().readLines("/day15/testChitonsAll.txt")).caveSpots.toTypedArray())
    }

    @Test
    fun `test chitons - solutionTwo - 315`() {
        val chitonNav = ChitonNav(FileReader().readLines("/day15/testChitons.txt"))

        val actual = chitonNav.solutionTwo(Spot(0,0), Spot(49,49))

        assertThat(actual).isEqualTo(315)
    }

    @Test
    fun `chitons - solutionTwo - 2821`() {
        val chitonNav = ChitonNav(FileReader().readLines("/day15/chitons.txt"))

        val actual = chitonNav.solutionTwo(Spot(0,0), Spot(499,499))

        assertThat(actual).isEqualTo(2821)
    }

}