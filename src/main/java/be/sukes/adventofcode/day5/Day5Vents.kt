package be.sukes.adventofcode.day5

class VentGrid{
    var grid : MutableList<GridSpot> = mutableListOf()

    fun traceLine(lineString: String)  {
        addVentLine(lineString.toVentLines().first())
    }

    data class GridSpot(val coordinate: Coordinate, var numberOfVents : Int = 1 ) {
        fun sameLocation(coordinate: Coordinate) =
                (this.coordinate.x == coordinate.x) and (this.coordinate.y == coordinate.y)

        fun addVentLine() =
                numberOfVents ++
    }

    /*0,2 -> 0,5*/
    private fun addVentLine(ventLine: VentLine) {
        if(ventLine is VerticalVentLine){
            for(i in ventLine.start.y .. ventLine.end.y){
                addGridSpots(ventLine, i) { line: VentLine, index: Int -> Coordinate(line.start.x,index)}
            }
        }else if(ventLine is HorizontalVentLine){
            for(i in ventLine.start.x .. ventLine.end.x){
                addGridSpots(ventLine, i) { line: VentLine, index: Int -> Coordinate(index,line.start.y)}
            }
        }
    }

    private fun addGridSpots(ventLine: VentLine, i: Int, directionPointer : (VentLine, Int) -> Coordinate) {
        val spotAtLocation = grid.filter { spot -> spot.sameLocation(directionPointer(ventLine,i)) }
        if (spotAtLocation.isEmpty()) {
            grid.add(GridSpot(directionPointer(ventLine,i)))
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
                .filterOutInvalidLines()
                .swapToPointAscending()
                .mapToVentLine()
                .toList()

private fun Sequence<List<Coordinate>>.swapToPointAscending(): Sequence<List<Coordinate>> =
    this.map { (start, end) ->
        if (pointedDescending(start, end)) listOf(end, start) else listOf(start, end)
    }

private fun pointedDescending(start: Coordinate, end: Coordinate) =
        start.x + start.y > end.x + end.y

private fun Sequence<List<Coordinate>>.mapToVentLine(): Sequence<VentLine> =
    this.map { (start, end) ->
        if (start.x == end.x) {
            VerticalVentLine(start, end)
        } else {
            HorizontalVentLine(start, end)
        }
    }


private fun Sequence<List<Coordinate>>.filterOutInvalidLines(): Sequence<List<Coordinate>> =
    this.filter { (start, end) ->
        start.x == end.x || start.y == end.y
    }


private fun String.toCoordinates(): Sequence<Coordinate> {
    return this.split(",", " -> ")
            .asSequence()
            .windowed(2, 2)
            .map { (x, y) ->
                Coordinate(x.toInt(), y.toInt())
            }
}
