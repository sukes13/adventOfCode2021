package be.sukes.adventofcode.day20

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day20EnhanceTest {

    @Nested
    inner class CreationTests {
        @Test
        fun `test enhance - create Enhancer - input image`() {
            val actual = Enhancer(FileReader().readLines("/day20/testInput.txt"))

            assertThat(actual.checkList).contains(10, 20)
            assertThat(actual.checkList).doesNotContain(60, 12)
            assertThat(actual.roster.print()).containsExactly("#..#.",
                    "#....",
                    "##..#",
                    "..#..",
                    "..###")
        }
    }

    @Nested
    inner class NormalEnhanceTests {
        @Test
        fun `test enhance - enhance once - output image is OK`() {
            val actual = Enhancer(FileReader().readLines("/day20/testInput.txt"))

            actual.enhance(1)

            assertThat(actual.roster.print()).isEqualTo(FileReader().readLines("/day20/testFirstEnhance.txt"))
        }

        @Test
        fun `test enhance - enhance twice - output image is OK`() {
            val actual = Enhancer(FileReader().readLines("/day20/testInput.txt"))

            actual.enhance(2)

            assertThat(actual.roster.print()).isEqualTo(FileReader().readLines("/day20/testSecondEnhance.txt"))
        }

        @Test
        fun `test enhance - solutionOne - is 35`() {
            val enhancer = Enhancer(FileReader().readLines("/day20/testInput.txt"))

            val actual = enhancer.solution(2)

            assertThat(actual).isEqualTo(35)
        }

        @Test
        fun `test enhance - solutionTwo - is 3351`() {
            val enhancer = Enhancer(FileReader().readLines("/day20/testInput.txt"))

            val actual = enhancer.solution(50)

            assertThat(actual).isEqualTo(3351)
        }
    }

    @Nested
    inner class FlippedEnhanceTests {
        @Test
        fun `test enhance flipper - enhancer 4 time (even) - is 0`() {
            val enhancer = Enhancer(FileReader().readLines("/day20/testInputFlip.txt"))

            val actual = enhancer.solution(4)

            assertThat(actual).isEqualTo(0)
        }

        @Test
        fun `test enhance flipper - enhance once - output image is OK`() {
            val actual = Enhancer(FileReader().readLines("/day20/testInputFlip.txt"))

            actual.enhance(1)

            assertThat(actual.roster.print()).isEqualTo(FileReader().readLines("/day20/testFlipFirstEnhance.txt"))
        }

        @Test
        fun `test enhance flipper - enhance twice - output image is OK`() {
            val actual = Enhancer(FileReader().readLines("/day20/testInputFlip.txt"))

            actual.enhance(2)

            assertThat(actual.roster.print()).isEqualTo(FileReader().readLines("/day20/testFlipSecondEnhance.txt"))
        }


        @Test
        fun `enhance - solutionOne - is 5379`() {
            val enhancer = Enhancer(FileReader().readLines("/day20/input.txt"))

            val actual = enhancer.solution(2)

            assertThat(actual).isEqualTo(5379)
        }

        @Test
        fun `enhance - solutionTwo - is 17917`() {
            val enhancer = Enhancer(FileReader().readLines("/day20/input.txt"))

            val actual = enhancer.solution(50)

            assertThat(actual).isEqualTo(17917)
        }
    }
}