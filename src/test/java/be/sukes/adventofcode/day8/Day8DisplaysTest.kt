package be.sukes.adventofcode.day8

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day8DisplaysTest {
    @Test
    fun `test input - create DisplayChecker`() {
        val input = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"

        val actual = Display(input)

        SoftAssertions().apply {
            assertThat(actual.inDigits).containsExactly("acedgfb","cdfbe","gcdfa","fbcad","dab","cefabd","cdfgeb","eafb","cagedb","ab")
            assertThat(actual.outDigits).containsExactly("cdfeb","fcadb","cdfeb","cdbaf")
        }
    }

    @Test
    fun `test input first - detect digit = empty`() {
        val display = Display("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")

        val actual = display.detectEasyOutputDigits()

        assertThat(actual).isEmpty()
    }

    @Test
    fun `test input first - detect digit = 1`() {
        val display = Display("be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe cgb gc")

        val actual = display.detectEasyOutputDigits()

        assertThat(actual).containsExactly(8 to "fdgacbe",
                                            4 to "gcbe",
                                            7 to "cgb",
                                            1 to "gc")
    }

    @Test
    fun `test input - solutionOne = 26`() {
        val entries = FileReader().readLines("/day8/testEntries.txt")

        val actual = entries.solutionOne()

        assertThat(actual).isEqualTo(26)
    }

    @Test
    fun `input - solutionOne = solution`() {
        val entries = FileReader().readLines("/day8/entries.txt")

        val actual = entries.solutionOne()

        assertThat(actual).isEqualTo(416)
    }
}

