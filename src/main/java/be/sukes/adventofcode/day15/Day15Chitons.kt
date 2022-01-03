package be.sukes.adventofcode.day15

import kotlin.random.Random


class ChitonNav(input: List<String>) {
    val caveSpots = input.toCaveSpots()

    fun shortestTo(start: Spot, end: Spot): Int {
        val queue = caveSpots.toStartQueue(start)
        val checked = mutableListOf<Int>()

        while(checked.size < caveSpots.keys.size){
            val current = queue.minBy {  it.value}!!.key
            checked.add(current.id)

            current.neighbours().forEach { (id, caveSpot) ->
                if(id !in checked){
                    val sum = queue.getValue(current) + caveSpot.risk
                    if(caveSpot.spot == end){
                        return sum
                    }
                    if(sum<queue.getValue(caveSpot)) queue[caveSpot] = sum
                }
            }
            queue.remove(current)
        }

        throw DestinationNotFoundException()
    }

    private fun MutableMap<Int,CaveSpot>.toStartQueue(start: Spot) =
            this.map { (_, caveSpot) ->
                when (caveSpot.spot) {
                    start -> caveSpot to 0
                    else -> caveSpot to Int.MAX_VALUE
                }
            }.toMap().toMutableMap()

    private fun CaveSpot.neighbours() =
            caveSpots.filter { (_, caveSpot) ->
                caveSpot.spot in this.spot.neighbours()
            }
}

private fun List<String>.toCaveSpots() =
        this.mapIndexed { row, line ->
            line.trim().mapIndexed { col, risk ->
                val caveSpot = CaveSpot(spot = Spot(col, row), risk = risk.toString().toInt())
                caveSpot.id to caveSpot
            }
        }.flatten().map { it.first to it.second }.toMap().toMutableMap()

data class CaveSpot(val id: Int = Random.nextInt(), val spot: Spot, val risk: Int)

data class Spot(val x: Int, val y: Int) {

    fun neighbours() =  listOf(Spot(x-1,y),Spot(x+1,y),Spot(x,y+1),Spot(x,y-1))
}

class DestinationNotFoundException : Throwable()
