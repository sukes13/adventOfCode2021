package be.sukes.adventofcode.day9

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test

class Day9HeightMapTest {

    private val testInput = """2199943210
                               3987894921
                               9856789892
                               8767896789
                               9899965678"""

    @Test
    fun `test floor - createHeightMap - contains (0 to 0,2), (5 to 0,4), (2 to 2,5), (9 to 4,8)`() {
        val actual = HeightMap(testInput.lines())

        assertThat(actual.floor).contains(entry(0 to 0,2),entry(5 to 0,4),entry(2 to 2,5),entry(9 to 4,8))
    }

    @Test
    fun `test floor - neighboursOf 2,2 - contains (2 to 1,8), (1 to 2,8), (2 to 3,6), (3 to 2,6)`() {
        val heightMap = HeightMap(testInput.lines())

        val actual = heightMap.neighboursOf(2 to 2)

        assertThat(actual).contains(entry(2 to 1,8),entry(1 to 2,8),entry(2 to 3,6),entry(3 to 2,6))
    }

    @Test
    fun `test floor - neighboursOf 0,0 - contains (0 to 1,3), (1 to 0,1)`() {
        val heightMap = HeightMap(testInput.lines())

        val actual = heightMap.neighboursOf(0 to 0)

        assertThat(actual).contains(entry(0 to 1,3),entry(1 to 0,1))
    }

    @Test
    fun `test floor - neighboursOf 0,2 - contains  (1 to 0,1), (3 to 0,9), (2 to 1,8)`() {
        val heightMap = HeightMap(testInput.lines())

        val actual = heightMap.neighboursOf(2 to 0)

        assertThat(actual).contains(entry(1 to 0,1),entry(3 to 0,9),entry(2 to 1,8))
    }

    @Test
    fun `test floor - findLowPoints - contains (1 to 0,1), (9 to 0,0), (2 to 2,5), (6 to 4,5)`() {
        val heightMap = HeightMap(testInput.lines())

        val actual = heightMap.lowPoints()

        assertThat(actual).contains(entry(1 to 0,1),entry(9 to 0,0),entry(2 to 2,5),
                entry(6 to 4,5))

    }

    @Test
    fun `test floor - solution1 is 15`() {
        val heightMap = HeightMap(testInput.lines())

        val actual = heightMap.solutionOne()

        assertThat(actual).isEqualTo(15)
    }

    @Test
    fun `floor - solution1 is 423`() {
        val heightMap = HeightMap(FileReader().readLines("/day9/floor.txt"))

        val actual = heightMap.solutionOne()

        assertThat(actual).isEqualTo(423)
    }

}
