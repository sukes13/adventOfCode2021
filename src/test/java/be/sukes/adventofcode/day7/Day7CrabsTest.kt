package be.sukes.adventofcode.day7

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day7CrabsTest {

    @Test
    fun `test crabs - correct CrabsRecord`() {
        val crabsString = "16,1,2,0,4,2,7,1,2,14"
        val actual = CrabsRecord(crabsString)

        assertThat("$actual").isEqualTo(crabsString)
    }

    @Test
    fun `test crabs - fuelCostTo 2 - cost is 37 `() {
        val crabsRecord = CrabsRecord("16,1,2,0,4,2,7,1,2,14")

        val actual = crabsRecord.fuelCost(2)

        assertThat(actual).isEqualTo(37)
    }

    @Test
    fun `test crabs - fuelCostTo 1 - cost is 41 `() {
        val crabsRecord = CrabsRecord("16,1,2,0,4,2,7,1,2,14")

        val actual = crabsRecord.fuelCost(1)

        assertThat(actual).isEqualTo(41)
    }

    @Test
    fun `test crabs - alignPosition - position is 2`() {
        val crabsRecord = CrabsRecord("16,1,2,0,4,2,7,1,2,14")

        val actual = crabsRecord.solution1()

        assertThat(actual.first).isEqualTo(2)
        assertThat(actual.second).isEqualTo(37)
    }

    @Test
    fun `crabs - alignPosition - position is solution`() {
        val crabsRecord = CrabsRecord(FileReader().readLines("/day7/crabs.txt").single())

        val actual = crabsRecord.solution1()

        assertThat(actual.first).isEqualTo(347)
        assertThat(actual.second).isEqualTo(347449)
    }

    @Test
    fun `test crabs - correctFuelCost 5 - cost is 168 `() {
        val crabsRecord = CrabsRecord("16,1,2,0,4,2,7,1,2,14")

        val actual = crabsRecord.correctFuelCost(5)

        assertThat(actual).isEqualTo(168)
    }


    @Test
    fun `test crabs - correctFuelCost 2 - cost is 206 `() {
        val crabsRecord = CrabsRecord("16,1,2,0,4,2,7,1,2,14")

        val actual = crabsRecord.correctFuelCost(2)

        assertThat(actual).isEqualTo(206)
    }


    @Test
    fun `crabs - correct alignPosition - position is solution`() {
        val crabsRecord = CrabsRecord(FileReader().readLines("/day7/crabs.txt").single())

        val actual = crabsRecord.solution2()

        assertThat(actual.first).isEqualTo(486)
        assertThat(actual.second).isEqualTo(98039527)
    }


}