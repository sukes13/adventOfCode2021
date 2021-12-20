package be.sukes.adventofcode.day9

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
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

        assertThat(actual.floor).contains(Spot(Coordinate(0 , 0),2),
                                            Spot(Coordinate(5 , 0),4),
                                            Spot(Coordinate(2 , 2),5),
                                            Spot(Coordinate(9 , 4),8))
    }

    @Test
    fun `test floor - neighboursOf 2,2 - contains (2 to 1,8), (1 to 2,8), (2 to 3,6), (3 to 2,6)`() {
        val heightMap = HeightMap(testInput.lines())

        val actual = heightMap.neighboursOf(Coordinate(2 , 2))

        assertThat(actual).contains(Spot(Coordinate(2 , 1),8),
                                    Spot(Coordinate(1 , 2),8),
                                    Spot(Coordinate(2 , 3),6),
                                    Spot(Coordinate(3 , 2),6))
    }

    @Test
    fun `test floor - neighboursOf 0,0 - contains (0 to 1,3), (1 to 0,1)`() {
        val heightMap = HeightMap(testInput.lines())

        val actual = heightMap.neighboursOf(Coordinate(0 , 0))

        assertThat(actual).contains(Spot(Coordinate(0 , 1),3),
                                    Spot(Coordinate(1 , 0),1))
    }

    @Test
    fun `test floor - neighboursOf 0,2 - contains  (1 to 0,1), (3 to 0,9), (2 to 1,8)`() {
        val heightMap = HeightMap(testInput.lines())

        val actual = heightMap.neighboursOf(Coordinate(2 , 0))

        assertThat(actual).contains(Spot(Coordinate(1 , 0),1),
                                    Spot(Coordinate(3 , 0),9),
                                    Spot(Coordinate(2 , 1),8))
    }

    @Test
    fun `test floor - findLowPoints - contains (1 to 0,1), (9 to 0,0), (2 to 2,5), (6 to 4,5)`() {
        val heightMap = HeightMap(testInput.lines())

        val actual = heightMap.lowPoints()

        assertThat(actual).contains(Spot(Coordinate(1 , 0),1)
                ,Spot(Coordinate(9 , 0),0)
                ,Spot(Coordinate(2 , 2),5)
                ,Spot(Coordinate(6 , 4),5))

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

    @Test
    fun `test floor - basinOf(1,0) - contains (1 to 0,1), (0 to 0,2), (0 to 1,1)`() {
        val heightMap = HeightMap(testInput.lines())

        val actual = heightMap.basinOf(Spot(Coordinate(1 , 0),1))

        assertThat(actual).containsOnly(Spot(Coordinate(0 , 0),2),
                                            Spot(Coordinate(1 , 0),1),
                                            Spot(Coordinate(0 , 1),3))
    }

    @Test
    fun `test floor - basinOf(9,0) - contains (1 to 0,1), (0 to 0,2), (0 to 1,1)`() {
        val heightMap = HeightMap(testInput.lines())

        val actual = heightMap.basinOf(Spot(Coordinate(9 , 0),0))

        assertThat(actual).containsOnly(Spot(coordinate=Coordinate(x=9, y=0), height=0),
                                        Spot(coordinate=Coordinate(x=8, y=0), height=1),
                                        Spot(coordinate=Coordinate(x=9, y=1), height=1),
                                        Spot(coordinate=Coordinate(x=7, y=0), height=2),
                                        Spot(coordinate=Coordinate(x=8, y=1), height=2),
                                        Spot(coordinate=Coordinate(x=9, y=2), height=2),
                                        Spot(coordinate=Coordinate(x=6, y=0), height=3),
                                        Spot(coordinate=Coordinate(x=5, y=0), height=4),
                                        Spot(coordinate=Coordinate(x=6, y=1), height=4))
    }

    @Test
    fun `test floor - basins - solution part 2 = 1134`() {
        val heightMap = HeightMap(testInput.lines())

        val actual = heightMap.solutionTwo()

        assertThat(actual).isEqualTo(1134)
    }

    @Test
    fun `floor - basins - solution part 2 = 1198704`() {
        val heightMap = HeightMap(FileReader().readLines("/day9/floor.txt"))

        val actual = heightMap.solutionTwo()

        assertThat(actual).isEqualTo(1198704)
    }
}
