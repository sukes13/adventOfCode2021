package be.sukes.adventofcode.day5

import be.sukes.adventofcode.day5.VentGrid.GridSpot
import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day5VentsTest {

    @Test
    fun `0,9 to 5,9 - toVentLines - correct HorizontalVentLine`() {
        val actual = "0,9 -> 5,9".toVentLine()

        assertThat(actual).isEqualTo(HorizontalVentLine(Coordinate(0,9),Coordinate(5,9)))
    }

    @Test
    fun `0,5 to 0,9 - toVentLines - correct VerticalVentLine`() {
        val actual = "0,5 -> 0,9".toVentLine()

        assertThat(actual).isEqualTo(VerticalVentLine(Coordinate(0,5),Coordinate(0,9)))
    }

    @Test
    fun `0,4 to 5,9 - toVentLines - no VentLine created`() {
        val actual = "0,7 -> 5,9".toVentLine()

        assertThat(actual).isNull()
    }

    @Test
    fun `0,9 to 0,5 - toVentLines - correct VerticalVentLine`() {
        val actual = "0,9 -> 0,5".toVentLine()

        assertThat(actual).isEqualTo(VerticalVentLine(Coordinate(0,9),Coordinate(0,5)))
    }

    @Test
    fun `1,1 to 3,3 - toVentLines - correct DiagonalVentLine`() {
        val actual = "1,1 -> 3,3".toVentLine()

        assertThat(actual).isEqualTo(DiagonalVentLine(Coordinate(1,1),Coordinate(3,3)))
    }

    @Test
    fun `9,7 to 7,9 - toVentLines - correct DiagonalVentLine`() {
        val actual = "9,7 -> 7,9".toVentLine()

        assertThat(actual).isEqualTo(DiagonalVentLine(Coordinate(9,7),Coordinate(7,9)))
    }

    //--------------------------------
    // Tracing vents tests
    //--------------------------------

    @Test
    fun `create VentGrid - traceLine "0,2 to 0,5" - 4 gridSpots (vertical) added`() {
        val ventGrid= VentGrid()
        ventGrid.traceLine("0,2 -> 0,5")

        assertThat(ventGrid.grid).hasSize(4)
        assertThat(ventGrid.grid).containsExactly(GridSpot(Coordinate(0, 2),1),
                                                    GridSpot(Coordinate(0, 3),1),
                                                    GridSpot(Coordinate(0, 4),1),
                                                    GridSpot(Coordinate(0, 5),1))
    }

    @Test
    fun `create VentGrid - traceLine twice - 4 gridSpots (vertical) with numberOfVents = 2`() {
        val ventGrid= VentGrid()
        ventGrid.traceLine("0,2 -> 0,5")
        ventGrid.traceLine("0,2 -> 0,5")

        assertThat(ventGrid.grid).hasSize(4)
        assertThat(ventGrid.grid).containsExactly(GridSpot(Coordinate(0, 2),2),
                                                    GridSpot(Coordinate(0, 3),2),
                                                    GridSpot(Coordinate(0, 4),2),
                                                    GridSpot(Coordinate(0, 5),2))
    }

    @Test
    fun `create VentGrid - traceLine "2,0 to 4,0" - 3 gridSpots (horizontal) added`() {
        val ventGrid= VentGrid()
        ventGrid.traceLine("2,0 -> 4,0")

        assertThat(ventGrid.grid).hasSize(3)
        assertThat(ventGrid.grid).containsExactly(GridSpot(Coordinate(2, 0),1),
                                                GridSpot(Coordinate(3, 0),1),
                                                GridSpot(Coordinate(4, 0),1))
    }

    @Test
    fun `create VentGrid - traceLine "0,5 to 0,2" - 4 gridSpots (vertical) added`() {
        val ventGrid= VentGrid()
        ventGrid.traceLine("0,5 -> 0,2")

        assertThat(ventGrid.grid).hasSize(4)
        assertThat(ventGrid.grid).containsExactlyInAnyOrder(GridSpot(Coordinate(0, 2),1),
                                                            GridSpot(Coordinate(0, 3),1),
                                                            GridSpot(Coordinate(0, 4),1),
                                                            GridSpot(Coordinate(0, 5),1))
    }

    @Test
    fun `create VentGrid - traceLine "0,5 to 0,2" - 3 gridSpots (horizontal) with numberOfVents = 2`() {
        val ventGrid= VentGrid()
        ventGrid.traceLine("2,0 -> 4,0")
        ventGrid.traceLine("2,0 -> 4,0")

        assertThat(ventGrid.grid).hasSize(3)
        assertThat(ventGrid.grid).containsExactly(GridSpot(Coordinate(2, 0),2),
                                                    GridSpot(Coordinate(3, 0),2),
                                                    GridSpot(Coordinate(4, 0),2))
    }

    @Test
    fun `create VentGrid - traceLine "1,1 to 1,3" and "0,0 to 3,0" (crossing) - gridSpot at 1,1 has numberOfVents = 2`() {
        val ventGrid= VentGrid()
        ventGrid.traceLine("1,1 -> 1,3")
        ventGrid.traceLine("1,1 -> 3,1")

        assertThat(ventGrid.grid).containsExactly(GridSpot(Coordinate(1, 1), 2),
                                                    GridSpot(Coordinate(1, 2), 1),
                                                    GridSpot(Coordinate(1, 3), 1),
                                                    GridSpot(Coordinate(2, 1), 1),
                                                    GridSpot(Coordinate(3, 1), 1))

    }

    @Test
    internal fun `testVents - solve - dangerSpots = 12`() {
        val ventLineString = FileReader().readLines("/day5/testVents.txt")

        val actual : Int = Day5Vents().solve(ventLineString)

        assertThat(actual).isEqualTo(12)
    }

//    @Test
//    internal fun `testVents - solve - dangerSpots = solution`() {
//        val ventLineString = FileReader().readLines("/day5/vents.txt")
//
//        val actual : Int = Day5Vents().solve(ventLineString)
//
//        assertThat(actual).isEqualTo(20666)
//    }

    @Test
    fun `create VentGrid - traceLine "1,1 to 3,3" - 3 gridSpots (diagonal) added`() {
        val ventGrid= VentGrid()
        ventGrid.traceLine("1,1 -> 3,3")

        assertThat(ventGrid.grid).hasSize(3)
        assertThat(ventGrid.grid).containsExactlyInAnyOrder(GridSpot(Coordinate(1, 1)),
                GridSpot(Coordinate(2, 2)),
                GridSpot(Coordinate(3, 3)))
    }

    @Test
    fun `create VentGrid - traceLine "9,7 to 7,9" - 3 gridSpots (diagonal) added`() {
        val ventGrid= VentGrid()
        ventGrid.traceLine("9,7 -> 7,9")

        assertThat(ventGrid.grid).hasSize(3)
        assertThat(ventGrid.grid).containsExactlyInAnyOrder(GridSpot(Coordinate(9, 7)),
                GridSpot(Coordinate(8, 8)),
                GridSpot(Coordinate(7, 9)))
    }
}
