package be.sukes.adventofcode.day14

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test

class Day14PolymerTest {

    @Test
    fun `create PolymerTemplate - is corret`() {
        val polymerTemplate = PolymerTemplate(FileReader().readLines("/day14/testTemplate.txt"))

        assertThat(polymerTemplate.startPolymer.pairs).contains(entry(('N' to 'N') , 1), entry(('N' to 'C') , 1) , entry(('C' to 'B') , 1))
        assertThat(polymerTemplate.startPolymer.letterCount).contains(entry('N',2), entry('C',1), entry('B',1))
        assertThat(polymerTemplate.inserts.toList()).contains(('C' to 'N') to 'C',
                                                            ('C' to 'H') to 'B',
                                                            ('H' to 'H') to 'N',
                                                            ('C' to 'B') to 'H')
    }

    @Test
    fun `test template - one insert - NCNBCHB`() {
        val polymerTemplate = PolymerTemplate(FileReader().readLines("/day14/testTemplate.txt"))

        val actual = polymerTemplate.insert(1)

        assertThat(actual.letterCount).contains(entry('B' ,2L),entry('C' , 2L),entry('N' , 2L),entry('H' , 1L))

    }







    @Test
    fun `test template - 4 inserts - is correct`() {
        val polymerTemplate = PolymerTemplate(FileReader().readLines("/day14/testTemplate.txt"))

        val actual = polymerTemplate.insert(4)

        assertThat(actual.letterCount).contains(entry('B' ,23L),entry('C' , 10L),entry('N' , 11L),entry('H' , 5L))
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


    @Test
    fun `test template - solutionTwo - 2188189693529`() {
        val polymerTemplate = PolymerTemplate(FileReader().readLines("/day14/testTemplate.txt"))

        val actual = polymerTemplate.solutionOne(40)

        assertThat(actual).isEqualTo(2188189693529L)
    }

    @Test
    fun `template - solutionTwo - 3018019237563L`() {
        val polymerTemplate = PolymerTemplate(FileReader().readLines("/day14/template.txt"))

        val actual = polymerTemplate.solutionOne(40)

        assertThat(actual).isEqualTo(3018019237563L)
    }
}


