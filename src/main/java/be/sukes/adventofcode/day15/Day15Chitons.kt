package be.sukes.adventofcode.day15


class ChitonNav(input: List<String>) {
    val caveSpots = input.toCaveSpots()

    fun solutionOne(start: Spot, end: Spot) =
            dijkstraScanForLowestTotalRisk(start, end, caveSpots)

    fun solutionTwo(start: Spot, end: Spot) =
            dijkstraScanForLowestTotalRisk(start, end, caveSpots.toTotalCave())

    private fun dijkstraScanForLowestTotalRisk(start: Spot, end: Spot, caveSpots: List<CaveSpot>): Int {
        val queue = caveSpots.toStartQueue(start)

        while (queue.isNotEmpty()) {
            val current = queue.minBy { it.value }!!
                    .also { queue.remove(it.key) }

            current.key.neighboursIn(queue).forEach { (neighbour, risk) ->
                val totalRiskToNeighbour = current.value + neighbour.risk
                if (neighbour.spot == end)
                    return totalRiskToNeighbour
                if (totalRiskToNeighbour < risk)
                    queue[neighbour] = totalRiskToNeighbour
            }

        }

        throw DestinationNotFoundException()
    }

    private fun List<CaveSpot>.toStartQueue(start: Spot) =
            this.map { caveSpot ->
                when (caveSpot.spot) {
                    start -> caveSpot to 0
                    else -> caveSpot to Int.MAX_VALUE
                }
            }.toMap().toMutableMap()

}

fun List<CaveSpot>.toTotalCave(): List<CaveSpot> {
    val width = this.maxBy { it.spot.x }!!.spot.x
    val height = this.maxBy { it.spot.y }!!.spot.y

    return this.flatMap { caveSpot ->
        caveSpot.horizontalRepeats(5, width).flatMap { caveSpot ->
            caveSpot.verticalRepeats(5, height)
        }
    }.sortedBy { it.spot.x }
}

private fun List<String>.toCaveSpots() =
        this.mapIndexed { row, line ->
            line.trim().mapIndexed { col, risk ->
                CaveSpot(spot = Spot(col, row), risk = risk.toString().toInt())
            }
        }.flatten()

data class CaveSpot(val spot: Spot, val risk: Int) {
    fun neighboursIn(caveSpots: MutableMap<CaveSpot, Int>) =
            caveSpots.filter { (caveSpot, _) ->
                caveSpot.spot in this.spot.neighbours()
            }

    fun horizontalRepeats(number: Int, width: Int) =
            (1 until number).fold(mutableListOf(this)) { reps, rep ->
                reps.plus(this.copy(spot = spot.repeatX(rep, width), risk = repeatRisk(risk, rep))).toMutableList()
            }


    fun verticalRepeats(number: Int, height: Int): List<CaveSpot> =
            (1 until number).fold(mutableListOf(this)) { reps, rep ->
                reps.plus(this.copy(spot = spot.repeatY(rep, height), risk = repeatRisk(risk, rep))).toMutableList()
            }

    private fun repeatRisk(risk: Int, repeat: Int) =
            when {
                risk + repeat < 10 -> risk + repeat
                else -> (risk + repeat) % 10 + 1
            }

}

data class Spot(val x: Int, val y: Int) {
    fun neighbours() = listOf(Spot(x - 1, y), Spot(x + 1, y), Spot(x, y + 1), Spot(x, y - 1))

    fun repeatX(repeat: Int, width: Int) =
            copy(x = x + repeat * (width + 1))

    fun repeatY(repeat: Int, width: Int) =
            copy(y = y + repeat * (width + 1))

}

class DestinationNotFoundException : Throwable()
