package be.sukes.adventofcode.day5

class Day5Vents {
    fun solve(ventLineString: List<String>): Int {
        val ventGrid = VentGrid()
        ventLineString.forEach {
            ventGrid.traceLine(it)
        }

        return ventGrid.numberOfDangerSpots()
    }


}
