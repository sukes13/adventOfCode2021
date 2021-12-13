package be.sukes.adventofcode.day5

data class Coordinate(val x : Int, val y : Int)
data class VentLine(val start: Coordinate, val end: Coordinate)

fun String.toVentLines(): List<VentLine> =
        this.split(",", " -> ")
                .asSequence()
                .windowed(2,2)
                .map { (x, y) -> Coordinate(x.toInt(), y.toInt()) }
                .windowed(2)
                .filter { (start,end) -> start.x == end.x || start.y == end.y }
                .map { (start, end) -> VentLine(start , end) }
                .toList()





