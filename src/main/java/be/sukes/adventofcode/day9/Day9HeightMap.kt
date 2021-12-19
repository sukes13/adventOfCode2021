package be.sukes.adventofcode.day9

class HeightMap(input: List<String>) {
    val floor : Map<Pair<Int,Int>,Int> = input.toFloor()

    fun solutionOne() = lowPoints()
            .map{it.value + 1}
            .sum()

    fun neighboursOf(location : Pair<Int,Int>) = floor.filter {
        it.key == location.first + 1 to location.second ||
        it.key == location.first - 1 to location.second ||
        it.key == location.first to location.second + 1 ||
        it.key == location.first to location.second - 1
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
                    (x to y) to height.toString().toInt()
                }.toMap()

}
