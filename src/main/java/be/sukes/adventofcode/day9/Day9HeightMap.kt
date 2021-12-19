package be.sukes.adventofcode.day9

class HeightMap(input: List<String>) {
    val floor : Map<Coordinate,Int> = input.toFloor()

    fun solutionOne() = lowPoints()
            .map{it.value + 1}
            .sum()

    fun neighboursOf(location : Coordinate) = floor.filter {
        it.key == Coordinate(location.x + 1 , location.y) ||
        it.key == Coordinate(location.x - 1 , location.y) ||
        it.key == Coordinate(location.x , location.y + 1) ||
        it.key == Coordinate(location.x , location.y - 1)
    }

    fun lowPoints() = floor.filter { spot ->
        val neighbours = neighboursOf(spot.key)
        neighbours.filter { neighbour ->
            neighbour.value > spot.value
        }.size == neighbours.size
    }

    private fun List<String>.toFloor() =
            this.mapIndexed { y, line ->
                       line.mapLine(y)
                    }.flatMap { it.toList() }.toMap()

    private fun String.mapLine(y: Int) =
            this.trim()
                .mapIndexed { x, height ->
                    Coordinate(x , y) to height.toString().toInt()
                }.toMap()

}

data class Coordinate(val x:Int,val y:Int)
