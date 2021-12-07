package be.sukes.adventofcode.day1

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day1SubmarineTest {

    @Test
    fun `do testsweep - count 7` () {
        val sweep : List<String> = FileReader().readLines("/testSweep.txt")

        val actual: Int = Day1Submarine().calculateDepthIncrease(sweep)

        assertThat(actual).isEqualTo(7)
    }

    @Test
    fun `do sweep - count answer` () {
        val sweep : List<String> = FileReader().readLines("/depthSweep.txt")

        val actual: Int = Day1Submarine().calculateDepthIncrease(sweep)

        assertThat(actual).isEqualTo(1121)
    }
}