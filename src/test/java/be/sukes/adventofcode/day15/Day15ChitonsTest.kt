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
    fun `test chitons - shortestTo - 40`() {
        val chitonNav = ChitonNav(FileReader().readLines("/day15/testChitons.txt"))

        val actual = chitonNav.shortestTo(Spot(0,0),Spot(9,9))

        assertThat(actual).isEqualTo(40)
    }

    @Test
    fun `chitons - shortestTo (solution one) - 487`() {
        val chitonNav = ChitonNav(FileReader().readLines("/day15/chitons.txt"))

        val actual = chitonNav.shortestTo(Spot(0,0),Spot(99,99))

        assertThat(actual).isEqualTo(487)
    }

}