package be.sukes.adventofcode.day14

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day14PolymerTest {

    @Test
    fun `create PolymerTemplate - is corret`() {
        val polymerTemplate = PolymerTemplate(FileReader().readLines("/day14/testTemplate.txt"))

        assertThat(polymerTemplate.startPolymer).containsExactly("N","N","C","B")
        assertThat(polymerTemplate.inserts).contains(Insert("C" to "N", "C"),
                                                     Insert("C" to "H", "B"),
                                                     Insert("H" to "H", "N"),
                                                     Insert("C" to "B", "H"))
    }

    @Test
    fun `test template - one insert - NCNBCHB`() {
        val polymerTemplate = PolymerTemplate(FileReader().readLines("/day14/testTemplate.txt"))

        val actual = polymerTemplate.insert(1)

        assertThat(actual.joinToString ("")).isEqualTo("NCNBCHB")

    }

    @Test
    fun `test template - 4 inserts - is correct`() {
        val polymerTemplate = PolymerTemplate(FileReader().readLines("/day14/testTemplate.txt"))

        val actual = polymerTemplate.insert(4)

        assertThat(actual.joinToString ("")).isEqualTo("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB")
    }

    @Test
    fun `test template - solutionOne - 1588`() {
        val polymerTemplate = PolymerTemplate(FileReader().readLines("/day14/testTemplate.txt"))

        val actual = polymerTemplate.solutionOne(10)

        assertThat(actual).isEqualTo(1588)
    }

    @Test
    fun `template - solutionOne - 2447`() {
        val polymerTemplate = PolymerTemplate(FileReader().readLines("/day14/template.txt"))

        val actual = polymerTemplate.solutionOne(10)

        assertThat(actual).isEqualTo(2447)
    }
}


