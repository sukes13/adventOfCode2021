package be.sukes.adventofcode.day11

import kotlin.random.Random


class OctopiRecord(startOctopi: List<String>) {
    private var octopi = startOctopi.toOctopi()

    fun solutionOne() = doSteps(100)

    fun doSteps(numberOfSteps: Int) =
            (1..numberOfSteps).fold(0) { num, _ ->
                num + doStep()
            }

    private fun doStep(): Int {
        var numberOfFlashes = 0
        octopi.forEach { (id, octopus) ->
            octopi[id] = octopus.copy(energy = octopus.energy + 1)
        }
        octopi = flashCascade(octopi)
        octopi.filter { it.value.flashed }.forEach { (id, octopus) ->
            octopi[id] = octopus.copy(energy = 0, flashed = false)
            numberOfFlashes += 1
        }
        return numberOfFlashes
    }

    private tailrec fun flashCascade(octopi: MutableMap<Int, Octopus>): MutableMap<Int, Octopus> {
        val readyToFlash = octopi.filter { it.value.energy >= 10 && !it.value.flashed }
        return when {
            readyToFlash.isEmpty() -> octopi
            else -> {
                readyToFlash.forEach { (id, octopus) ->
                    octopus.updateNeighbours(octopi)
                    octopi[id] = octopus.copy(flashed = true)
                }
                flashCascade(octopi)
            }
        }
    }


    private fun Octopus.updateNeighbours(octopi: MutableMap<Int, Octopus>) =
            this.neighbours()
                    .forEach { (neighbourId, neighbour) ->
                        octopi[neighbourId] = neighbour.copy(energy = neighbour.energy + 1)
                    }

    private fun Octopus.neighbours() =
            octopi.filter { (_, octopus) ->
                octopus.spot in this.spot.neighbours()
            }


    private fun List<String>.toOctopi() =
            this.mapIndexed { row, line ->
                line.trim().mapIndexed { col, energy ->
                    val octopus = Octopus(spot = Spot(col, row), energy = energy.toString().toInt())
                    octopus.id to octopus
                }
            }.flatten().map { it.first to it.second }.toMap().toMutableMap()

    fun current(): List<String> {
        return octopi.map { it.value }.groupBy { it.spot.y }
                .toSortedMap()
                .map {
                    it.value.groupBy { line -> line.spot.x }
                            .toSortedMap()
                            .map { octopus ->
                                octopus.value.single().energy.toString()
                            }.joinToString("")
                }
    }

}

data class Octopus(val id: Int = Random.nextInt(), val spot: Spot, val energy: Int = 0, val flashed: Boolean = false)

data class Spot(val x: Int, val y: Int) {
    fun neighbours(): List<Spot> {
        return (this.x - 1..this.x + 1).map { x ->
            (this.y - 1..this.y + 1).map { y ->
                Spot(x, y)
            }
        }.flatten().filterNot { it == this }
    }
}
