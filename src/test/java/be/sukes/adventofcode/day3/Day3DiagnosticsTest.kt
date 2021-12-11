package be.sukes.adventofcode.day3

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day3DiagnosticsTest {

    @Test
    fun `test diag - calculateGamma - gamma = 10110`() {
        val diag = FileReader().readLines("/day3/testDiagnostics.txt")

        val actual = Day3PowerDiagnostics().calculateGamma(diag)

        assertThat(actual.binary).isEqualTo("10110")
        assertThat(actual.decimal).isEqualTo(22)
    }

    @Test
    fun `test diag - calculateEpsilon - epsilon = 01001`() {
        val diag = FileReader().readLines("/day3/testDiagnostics.txt")

        val actual = Day3PowerDiagnostics().calculateEpsilon(diag)

        assertThat(actual.binary).isEqualTo("01001")
        assertThat(actual.decimal).isEqualTo(9)
    }

    @Test
    fun `test diag - powerUsage - powerUsage = 198`() {
        val diag = FileReader().readLines("/day3/testDiagnostics.txt")

        val actual = Day3PowerDiagnostics().powerUsage(diag)

        assertThat(actual).isEqualTo(198)
    }

    @Test
    fun `diag - powerUsage - powerUsage = 198`() {
        val diag = FileReader().readLines("/day3/diagnostics.txt")

        val actual = Day3PowerDiagnostics().powerUsage(diag)

        assertThat(actual).isEqualTo(3923414)
    }


}
