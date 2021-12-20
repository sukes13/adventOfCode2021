package be.sukes.adventofcode.day9

class HeightMap(input: List<String>) {
    val floor : Set<Spot> = input.toFloor()

    fun solutionOne() =
            lowPoints().map{it.height + 1}
                        .sum()

    fun solutionTwo() =
            lowPoints().asSequence().map { basinOf(it).size }
                                    .sortedByDescending { it }
                                    .take(3)
                                    .reduce(Int::times)

    fun neighboursOf(location : Coordinate) =
            floor.filter { spot ->
                spot.coordinate == Coordinate(location.x + 1, location.y) ||
                spot.coordinate == Coordinate(location.x - 1, location.y) ||
                spot.coordinate == Coordinate(location.x, location.y + 1) ||
                spot.coordinate == Coordinate(location.x, location.y - 1)
            }

    fun lowPoints() =
            floor.filter { spot ->
                val neighbours = neighboursOf(spot.coordinate)
                neighbours.filter { neighbour ->
                    neighbour.height > spot.height
                }.size == neighbours.size
            }

    fun basinOf(spot: Spot) = fillBasin(mutableSetOf(spot))

    private tailrec fun fillBasin(basin : MutableSet<Spot>): MutableSet<Spot> {
            val newSpots = basin.newBasinNeighbours()
            return when {
                        newSpots.isNotEmpty() -> {
                            basin.addAll(newSpots)
                            fillBasin(basin)
                        }
                        else  -> basin
            }
        }

    private fun Set<Spot>.newBasinNeighbours() =
            this.flatMap { spot ->
                        neighboursOf(spot.coordinate).filter { it.height != 9 }
                    }
                    .filterNot { it in this }
                    .toSet()

    private fun List<String>.toFloor() =
            this.mapIndexed { y, line ->
                line.mapLine(y)
            }.flatMap { it.toSet() }.toSet()

    private fun String.mapLine(y: Int) =
            this.trim()
                    .mapIndexed { x, height ->
                        Spot(Coordinate(x , y) , height.toString().toInt())
                    }
}

data class Coordinate(val x:Int,val y:Int)

data class Spot(val coordinate: Coordinate,val height : Int)