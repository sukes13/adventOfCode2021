package be.sukes.adventofcode.day11

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

val smallOctopiCasc = """11111
                         19991
                         19191
                         19991
                         11111""".prepare()

val smallOctopi = """11111
                     19991
                     19191
                     19991
                     11111""".prepare()

val smallOctopi2 = """34543
                        40004
                        50005
                        40004
                        34543""".prepare()

class Day11OctopiTest {

    @Test
    fun `small octopi - create Octopi - 25 octopus `() {
        val actual = OctopiRecord(smallOctopiCasc)

        assertThat(actual.current()).isEqualTo(smallOctopiCasc)
    }

    @Test
    fun `small octopi - doStep - new pattern `() {
        val octopiRecord = OctopiRecord(smallOctopi)

        octopiRecord.solutionOne(1)

        assertThat(octopiRecord.current()).isEqualTo(smallOctopi2)
    }

    @Test
    fun `test octopi - doStep - new pattern `() {
        val octopiRecord = OctopiRecord(FileReader().readLines("/day11/testOctopi.txt"))

        octopiRecord.solutionOne(1)

        assertThat(octopiRecord.current()).isEqualTo(FileReader().readLines("/day11/testOctopiStepOne.txt"))
    }

    @Test
    fun `test octopi - doStep twice - new pattern `() {
        val octopiRecord = OctopiRecord(FileReader().readLines("/day11/testOctopi.txt"))

        octopiRecord.solutionOne(2)

        assertThat(octopiRecord.current()).isEqualTo(FileReader().readLines("/day11/testOctopiStepTwo.txt"))
    }

    @Test
    fun `test octopi - doStep 3 times - new pattern `() {
        val octopiRecord = OctopiRecord(FileReader().readLines("/day11/testOctopi.txt"))

        octopiRecord.solutionOne(3)

        assertThat(octopiRecord.current()).isEqualTo(FileReader().readLines("/day11/testOctopiStepThree.txt"))
    }

    @Test
    fun `test octopi - doStep 100 times - new pattern `() {
        val octopiRecord = OctopiRecord(FileReader().readLines("/day11/testOctopi.txt"))

        octopiRecord.solutionOne(100)

        assertThat(octopiRecord.current()).isEqualTo(FileReader().readLines("/day11/testOctopiStepHunderd.txt"))
    }

    @Test
    fun `test octopi - solutionOne - 1656`() {
        val octopiRecord = OctopiRecord(FileReader().readLines("/day11/testOctopi.txt"))

        val actual = octopiRecord.solutionOne(100)

        assertThat(actual).isEqualTo(1656)
    }

    @Test
    fun `octopi - solutionOne - 1683`() {
        val octopiRecord = OctopiRecord(FileReader().readLines("/day11/octopi.txt"))

        val actual = octopiRecord.solutionOne(100)

        assertThat(actual).isEqualTo(1683)
    }

    @Test
    fun `test octopi - solutionTwo - 195`() {
        val octopiRecord = OctopiRecord(FileReader().readLines("/day11/testOctopi.txt"))

        val actual = octopiRecord.solutionTwo()

        assertThat(actual).isEqualTo(195)
    }

    @Test
    fun `octopi - solutionTwo - 1683`() {
        val octopiRecord = OctopiRecord(FileReader().readLines("/day11/octopi.txt"))

        val actual = octopiRecord.solutionTwo()

        assertThat(actual).isEqualTo(788)
    }
}

fun String.prepare() =
        this.replace(" ", "")
                .replace("\n", System.lineSeparator())
                .lines()

