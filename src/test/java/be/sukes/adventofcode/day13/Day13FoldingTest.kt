package be.sukes.adventofcode.day13

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day13FoldingTest {
    @Test
    fun `create Manual  - is correct`() {
        val manual = Manual(FileReader().readLines("/day13/testSheet.txt"))

        assertThat(manual.roster.spots).contains(Spot(6, 10), Spot(0,14), Spot(9,10), Spot(0,3), Spot(10,4))
        assertThat(manual.foldings).containsExactlyInAnyOrder(HorizontalFold(7), VerticalFold(5))
    }

    @Test
    fun `create Manual  - print`() {
        val manual = Manual(FileReader().readLines("/day13/testSheet.txt"))

        assertThat(manual.print()).containsExactly("...#..#..#.",
                                                    "....#......",
                                                    "...........",
                                                    "#..........",
                                                    "...#....#.#",
                                                    "...........",
                                                    "...........",
                                                    "...........",
                                                    "...........",
                                                    "...........",
                                                    ".#....#.##.",
                                                    "....#......",
                                                    "......#...#",
                                                    "#..........",
                                                    "#.#........")
    }

    @Test
    fun `test sheet - fold HorizontalFold  - print correct`() {
        val manual = Manual(FileReader().readLines("/day13/testSheet.txt"))

        val actual = HorizontalFold(7).doFold(manual.roster)

        assertThat(actual.print()).containsExactly("#.##..#..#.",
                                                    "#...#......",
                                                    "......#...#",
                                                    "#...#......",
                                                    ".#.#..#.###",
                                                    "...........",
                                                    "...........",
                                                    "...........")
    }

    @Test
    fun `test sheet - solutionOne - 17`() {
        val manual = Manual(FileReader().readLines("/day13/testSheet.txt"))

        val actual = manual.solutionOne()

        assertThat(actual).isEqualTo(17)
    }

    @Test
    fun `sheet - solutionOne - 729`() {
        val manual = Manual(FileReader().readLines("/day13/sheet.txt"))

        val actual = manual.solutionOne()

        assertThat(actual).isEqualTo(729)
    }

    @Test
    fun `sheet - solutionTwo - reads RGZLBHFP`() {
        val manual = Manual(FileReader().readLines("/day13/sheet.txt"))

        manual.fold()

        assertThat(manual.print()).isEqualTo(FileReader().readLines("/day13/foldedSheet.txt"))
    }
}


