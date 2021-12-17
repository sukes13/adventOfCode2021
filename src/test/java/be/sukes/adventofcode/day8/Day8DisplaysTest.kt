package be.sukes.adventofcode.day8

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
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
    fun `test input first - detectEasySignals digit = empty`() {
        val display = Display("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")

        val actual = display.outDigits.detectEasySignals()

        assertThat(actual).isEmpty()
    }

    @Test
    fun `test input first - detectEasySignals all found`() {
        val display = Display("be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe cgb gc")

        val actual = display.outDigits.detectEasySignals()

        assertThat(actual).containsExactly((8 to "fdgacbe"),
                                    (4 to "gcbe"),
                                    (7 to "cgb"),
                                    (1 to "gc"))
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

    @Test
    fun `test input first - detectSignals all found`() {
        val display = Display("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")

        val actual = display.detectSignals()

        assertThat(actual).contains(entry(0 , "cagedb"),
                                            entry(1 , "ab"),
                                            entry(2 , "gcdfa"),
                                            entry(3 , "fbcad"),
                                            entry(4 , "eafb"),
                                            entry(5 , "cdfbe"),
                                            entry(6 , "cdfgeb"),
                                            entry(7 , "dab"),
                                            entry(8 , "acedgfb"),
                                            entry(9 , "cefabd"))
    }

}

