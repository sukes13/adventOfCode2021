package be.sukes.adventofcode.day5

import be.sukes.adventofcode.day5.VentGrid.GridSpot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day5VentsTest {

    @Test
    fun `0,9 to 5,9 - toVentLines - correct HorizontalVentLine`() {
        val actual = "0,9 -> 5,9".toVentLines()

        assertThat(actual).hasSize(1)
        assertThat(actual[0]).isEqualTo(HorizontalVentLine(Coordinate(0,9),Coordinate(5,9)))
    }

    @Test
    fun `0,5 to 0,9 - toVentLines - correct VerticalVentLine`() {
        val actual = "0,5 -> 0,9".toVentLines()

        assertThat(actual).hasSize(1)
        assertThat(actual[0]).isEqualTo(VerticalVentLine(Coordinate(0,5),Coordinate(0,9)))
    }

    @Test
    fun `0,4 to 5,9 - toVentLines - no VentLine created`() {
        val actual = "0,4 -> 5,9".toVentLines()

        assertThat(actual).hasSize(0)
    }

    @Test
    fun `create VentGrid - traceLine "0,2 to 0,5" - 4 gridSpots (vertical) added`() {
        val ventGrid= VentGrid()
        ventGrid.traceLine("0,2 -> 0,5")

        assertThat(ventGrid.grid).hasSize(4)
        assertThat(ventGrid.grid).contains(GridSpot(Coordinate(0, 2),1),
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
        assertThat(ventGrid.grid).contains(GridSpot(Coordinate(0, 2),2),
                                            GridSpot(Coordinate(0, 3),2),
                                            GridSpot(Coordinate(0, 4),2),
                                            GridSpot(Coordinate(0, 5),2))
    }

    @Test
    fun `create VentGrid - traceLine "2,0 to 4,0" - 3 gridSpots (horizontal) added`() {
        val ventGrid= VentGrid()
        ventGrid.traceLine("2,0 -> 4,0")

        assertThat(ventGrid.grid).hasSize(3)
        assertThat(ventGrid.grid).contains(GridSpot(Coordinate(2, 0),1),
                                            GridSpot(Coordinate(3, 0),1),
                                            GridSpot(Coordinate(4, 0),1))
    }

//    @Test
//    fun `create VentGrid - traceLine "0,5 to 0,2" - 4 gridSpots (vertical) added`() {
//        val ventGrid= VentGrid()
//        ventGrid.traceLine("0,5 -> 0,2")
//
//        assertThat(ventGrid.grid).hasSize(4)
//        assertThat(ventGrid.grid).contains(GridSpot(Coordinate(0, 2),1),
//                GridSpot(Coordinate(0, 3),1),
//                GridSpot(Coordinate(0, 4),1),
//                GridSpot(Coordinate(0, 5),1))
//    }
}
