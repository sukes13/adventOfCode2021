package be.sukes.adventofcode.day5

class VentGrid{
    var grid : MutableList<GridSpot> = mutableListOf()

    fun traceLine(lineString: String)  {
        println("Tracing line: $lineString")
        addVentLine(lineString.toVentLine())
    }

    private fun addVentLine(ventLine: VentLine?) {
        val coordinates = ventLine?.coordinates()
        coordinates?.forEach {coordinate ->
            val spotAtLocation = grid.filter { spot -> spot.sameLocation(coordinate) }
            if (spotAtLocation.isEmpty()) {
                grid.add(GridSpot(coordinate))
            } else {
                spotAtLocation.first().addVentLine()
            }
        }
    }

    fun numberOfDangerSpots() =
        grid.filter { it.numberOfVents >= 2 }.count()

    data class GridSpot(val coordinate: Coordinate, var numberOfVents : Int = 1 ) {
        fun sameLocation(coordinate: Coordinate) =
                (this.coordinate.x == coordinate.x) and (this.coordinate.y == coordinate.y)

        fun addVentLine() =
                numberOfVents ++
    }
}

data class Coordinate(val x : Int, val y : Int)

abstract class VentLine(open val start: Coordinate, open val end: Coordinate) {
    abstract fun coordinates(): List<Coordinate>
}
data class HorizontalVentLine(override val start: Coordinate, override val end: Coordinate) : VentLine(start, end){
    override fun coordinates() : List<Coordinate> {
        val range = if (ascending(start,end)) start.x.rangeTo(end.x) else start.x.downTo(end.x)

        val coordinates : MutableList<Coordinate> = mutableListOf()
        for(i in range){
            coordinates.add(Coordinate(i, start.y))
        }
        return coordinates
    }
}
data class VerticalVentLine(override val start: Coordinate, override val end: Coordinate) : VentLine(start, end){
    override fun coordinates() : List<Coordinate> {
        val range = if (ascending(start,end)) start.y.rangeTo(end.y) else start.y.downTo(end.y)

        val coordinates : MutableList<Coordinate> = mutableListOf()
        for(i in range){
            coordinates.add(Coordinate(start.x,i))
        }
        return coordinates
    }

}
private fun ascending(start: Coordinate, end: Coordinate) = start.x + start.y < end.x + end.y

fun String.toVentLine(): VentLine? =  toCoordinates().mapToVentLine()

private fun Pair<Coordinate,Coordinate>.mapToVentLine(): VentLine? {
        if (this.first.x == this.second.x) {
            return VerticalVentLine(this.first, this.second)
        } else if(this.first.y == this.second.y){
            return HorizontalVentLine(this.first, this.second)
        }
        return null
}

private fun Pair<Coordinate,Coordinate>.filterOutInvalidLine(): Boolean =
    this.first.x == this.second.x || this.first.y == this.second.y



private fun String.toCoordinates(): Pair<Coordinate, Coordinate> {
    return this.split(",", " -> ")
            .asSequence()
            .windowed(2, 2)
            .map {  (x, y) ->
                Coordinate(x.toInt(), y.toInt())
            }.zipWithNext()
            .first()
}
