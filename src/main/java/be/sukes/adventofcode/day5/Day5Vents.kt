package be.sukes.adventofcode.day5

class VentGrid{
    var grid : MutableList<GridSpot> = mutableListOf()

    fun traceLine(lineString: String)  {
        addVentLine(lineString.toVentLines().first())
    }

    data class GridSpot(val coordinate: Coordinate, var numberOfVents : Int = 1 ) {
        fun sameLocation(x: Int, y: Int) =
                (coordinate.x == x) and (coordinate.y == y)

        fun addVentLine() =
            numberOfVents ++
    }

    /*0,2 -> 0,5*/
    private fun addVentLine(ventLine: VentLine) {
        if(ventLine is VerticalVentLine){
            for(i in ventLine.start.y .. ventLine.end.y){
                addVerticalVentLine(ventLine, i)
            }
        }else if(ventLine is HorizontalVentLine){
            for(i in ventLine.start.x .. ventLine.end.x){
                addHorizontalVentLine(ventLine, i)
            }
        }
    }

    private fun addVerticalVentLine(ventLine: VentLine, i: Int) {
        val spotAtLocation = grid.filter { spot -> spot.sameLocation(ventLine.start.x, i) }
        if (spotAtLocation.isEmpty()) {
            grid.add(GridSpot(Coordinate(ventLine.start.x, i)))
        } else {
            spotAtLocation.first().addVentLine()
        }
    }

    private fun addHorizontalVentLine(ventLine: VentLine, i: Int) {
        val spotAtLocation = grid.filter { spot -> spot.sameLocation(i,ventLine.start.y) }
        if (spotAtLocation.isEmpty()) {
            grid.add(GridSpot(Coordinate(i,ventLine.start.y)))
        } else {
            spotAtLocation.first().addVentLine()
        }
    }


}

data class Coordinate(val x : Int, val y : Int)
open class VentLine(open val start: Coordinate, open val end: Coordinate)
data class HorizontalVentLine(override val start: Coordinate, override val end: Coordinate) : VentLine(start, end)
data class VerticalVentLine(override val start: Coordinate, override val end: Coordinate) : VentLine(start, end)

fun String.toVentLines(): List<VentLine> =
        toCoordinates()
                .windowed(2)
                .filter { (start, end) ->
                    start.x == end.x || start.y == end.y
                }
                .map { (start, end) ->
                    if(start.x == end.x){
                        VerticalVentLine(start , end)
                    }else {
                        HorizontalVentLine(start , end)
                    }
                }
                .toList()

private fun String.toCoordinates(): Sequence<Coordinate> {
    return this.split(",", " -> ")
            .asSequence()
            .windowed(2, 2)
            .map { (x, y) ->
                Coordinate(x.toInt(), y.toInt())
            }
}






