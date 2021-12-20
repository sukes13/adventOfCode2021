package be.sukes.adventofcode.day9

class HeightMap(input: List<String>) {
    val floor : Set<Spot> = input.toFloor()

    fun solutionOne() =
            lowPoints().map{it.height + 1}
                        .sum()

    fun solutionTwo(): Int {
        return lowPoints().asSequence().map { basinOf(it) }
                                        .sortedByDescending { it.size }
                                        .take(3)
                                        .map { it.size }
                                        .reduce(Int::times)
    }

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

    fun basinOf(spot: Spot) : Set<Spot>  {
        val basin : MutableSet<Spot> = mutableSetOf()

        var notNines = setOf(spot)
        while (hasNewNotNineNeighbours(notNines, basin)){
            basin.addToSpots(notNines)
            notNines = notNines.map { newSpot ->
                val newNotNines = neighboursNotNine(newSpot)

                basin.addToSpots(newNotNines)
                return@map newNotNines
            }.flatten().toSet()
        }

        return basin
    }

    private fun List<String>.toFloor() =
            this.mapIndexed { y, line ->
                       line.mapLine(y)
                    }.flatMap { it.toSet() }.toSet()

    private fun String.mapLine(y: Int) =
            this.trim()
                .mapIndexed { x, height ->
                    Spot(Coordinate(x , y) , height.toString().toInt())
                }

    private fun hasNewNotNineNeighbours(notNines: Set<Spot>, basin: MutableSet<Spot>) =
            notNines.flatMap { neighboursNotNine(it) }.filterNot { it in basin }.isNotEmpty()

    private fun MutableSet<Spot>.addToSpots(notNine: Set<Spot>) {
        println("Add to basin -> $notNine")
        this.addAll(notNine)
    }

    private fun neighboursNotNine(spot: Spot): Set<Spot> {
        return neighboursOf(spot.coordinate).filter { neighbour ->
            neighbour.height != 9
        }.toSet()
    }

}

data class Coordinate(val x:Int,val y:Int)

data class Spot(val coordinate: Coordinate,val height : Int)