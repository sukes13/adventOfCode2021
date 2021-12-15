package be.sukes.adventofcode.day5

class Day5Vents {
    fun solve(ventLineString: List<String>): Int {
        val ventGrid = VentGrid()
        ventLineString.forEach {
            ventGrid.traceLine(it)
        }

        val numberOfDangerSpots = ventGrid.numberOfDangerSpots()
        println("Number Of Danger Spots: $numberOfDangerSpots" )
        return numberOfDangerSpots
    }


}
