package be.sukes.adventofcode.day5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day5VentsTest {

    @Test
    fun `0,9 to 5,9 - toVentLines - correct VentLine`() {
        val actual = "0,9 -> 5,9".toVentLines()

        assertThat(actual).hasSize(1)
        assertThat(actual[0]).isEqualTo(VentLine(Coordinate(0,9),Coordinate(5,9)))
    }

    @Test
    fun `0,4 to 5,9 - toVentLines - no VentLine created`() {
        val actual = "0,4 -> 5,9".toVentLines()

        assertThat(actual).hasSize(0)
    }
}
