package be.sukes.adventofcode.day5

import kotlin.math.absoluteValue

class VentGrid{
    val grid : MutableList<GridSpot> = mutableListOf()

    fun traceLine(lineString: String)  {
        println("Tracing line: $lineString")
        lineString.toVentLine()
                  .addVentLine()
    }

    fun numberOfDangerSpots() =
        grid.filter { it.numberOfVents >= 2 }
            .count()

    private fun VentLine?.addVentLine() {
        this?.coordinates()?.forEach {coordinate ->
            val spotAtLocation = grid.filter { spot -> spot.sameLocation(coordinate) }
            if (spotAtLocation.isEmpty())
                grid.add(GridSpot(coordinate))
            else
                spotAtLocation.single().addVentLine()
        }
    }

    data class GridSpot(val coordinate: Coordinate, var numberOfVents : Int = 1 ) {
        fun sameLocation(coordinate: Coordinate) =
                this.coordinate == coordinate
        fun addVentLine() =
                numberOfVents ++
    }
}

data class Coordinate(val x : Int, val y : Int)

abstract class VentLine(open val start: Coordinate, open val end: Coordinate) {
    abstract fun coordinates(): List<Coordinate>
}
data class HorizontalVentLine(override val start: Coordinate, override val end: Coordinate) : VentLine(start, end){
    override fun coordinates() = start.x.straightTo(end.x)
                                                      .map{ index -> Coordinate(index,start.y)}

}
data class VerticalVentLine(override val start: Coordinate, override val end: Coordinate) : VentLine(start, end){
    override fun coordinates() = start.y.straightTo(end.y)
                                                      .map{ index -> Coordinate(start.x,index)}
}
data class DiagonalVentLine(override val start: Coordinate, override val end: Coordinate) : VentLine(start, end) {
    override fun coordinates(): List<Coordinate> {
        val horizontalRange = start.x.straightTo(end.x)
        val verticalRange = start.y.straightTo(end.y)

        return horizontalRange.toList().zip(verticalRange.toList(), ::Coordinate)
    }
}

private fun Int.straightTo(end: Int) =
        if (this < end) this.rangeTo(end) else this.downTo(end)

fun String.toVentLine(): VentLine? =
        definingCoordinates().toVentLine()

private fun String.definingCoordinates() =
            split(",", " -> ")
            .windowed(2, 2)
            .map {  (x, y) ->
                Coordinate(x.toInt(), y.toInt())
            }.zipWithNext()
            .single()


private fun Pair<Coordinate,Coordinate>.toVentLine(): VentLine? =
    when {
        isVertical() -> VerticalVentLine(first, second)
        isHorizontal() -> HorizontalVentLine(first, second)
        isDiagonal() -> DiagonalVentLine(first, second)
        else -> null
    }


private fun Pair<Coordinate, Coordinate>.isVertical() =
        first.x == second.x

private fun Pair<Coordinate, Coordinate>.isHorizontal() =
        first.y == second.y

private fun Pair<Coordinate, Coordinate>.isDiagonal() =
        (first.x - second.x).absoluteValue == (first.y - second.y).absoluteValue
