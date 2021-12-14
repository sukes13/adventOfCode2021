package be.sukes.adventofcode.day5

import kotlin.math.absoluteValue

class VentGrid{
    var grid : MutableList<GridSpot> = mutableListOf()

    fun traceLine(lineString: String)  {
        println("Tracing line: $lineString")
        addVentLine(lineString.toVentLine())
    }

    private fun addVentLine(ventLine: VentLine?) {
        ventLine?.coordinates()?.forEach {coordinate ->
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
        fun sameLocation(coordinate: Coordinate) = this.coordinate == coordinate

        fun addVentLine() =
                numberOfVents ++
    }
}

data class Coordinate(val x : Int, val y : Int)

abstract class VentLine(open val start: Coordinate, open val end: Coordinate) {
    abstract fun coordinates(): List<Coordinate>

    protected fun coordinatesOfRange(range : IntProgression,directionPointer : (Int) -> Coordinate): MutableList<Coordinate> {
        val coordinates : MutableList<Coordinate> = mutableListOf()
        for(i in range){
            coordinates.add(directionPointer(i))
        }
        return coordinates
    }
}
data class HorizontalVentLine(override val start: Coordinate, override val end: Coordinate) : VentLine(start, end){
    override fun coordinates() : List<Coordinate> {
        val range = start.x.straightTo( end.x)
        return coordinatesOfRange(range){ index: Int -> Coordinate(index,start.y)}
    }
}
data class VerticalVentLine(override val start: Coordinate, override val end: Coordinate) : VentLine(start, end){
    override fun coordinates() : List<Coordinate> {
        val range = start.y.straightTo(end.y)
        return coordinatesOfRange(range){ index: Int -> Coordinate(start.x,index)}
    }
}
data class DiagonalVentLine(override val start: Coordinate, override val end: Coordinate) : VentLine(start, end) {
    override fun coordinates(): List<Coordinate> {
        val horizontalRange = start.x.straightTo(end.x)
        val verticalRange = start.y.straightTo(end.y)

        return horizontalRange.toList().zip(verticalRange.toList()){ x,y ->
            Coordinate(x,y)
        }
    }
}

private fun Int.straightTo(end: Int) = if (this < end) this.rangeTo(end) else this.downTo(end)

fun String.toVentLine(): VentLine? =  toCoordinates().mapToVentLine()

private fun String.toCoordinates(): Pair<Coordinate, Coordinate> {
    return this.split(",", " -> ")
            .asSequence()
            .windowed(2, 2)
            .map {  (x, y) ->
                Coordinate(x.toInt(), y.toInt())
            }.zipWithNext()
            .first()
}

private fun Pair<Coordinate,Coordinate>.mapToVentLine(): VentLine? {
    if (this.first.x == this.second.x) {
        return VerticalVentLine(this.first, this.second)
    } else if(this.first.y == this.second.y){
        return HorizontalVentLine(this.first, this.second)
    } else if((this.first.x - this.second.x).absoluteValue == (this.first.y - this.second.y).absoluteValue){
        return DiagonalVentLine(this.first, this.second)
    }
    return null
}
