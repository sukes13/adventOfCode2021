package be.sukes.adventofcode.day15


class ChitonNav(input: List<String>) {
    val caveSpots = input.toCaveSpots()

    fun shortestTo(start: Spot, end: Spot): Int {
        val queue = caveSpots.toStartQueue(start)
        val checked = mutableListOf<CaveSpot>()

        while(checked.size < caveSpots.size){
            val current = queue.minBy {  it.value}!!.key
            checked.add(current)

            current.neighbours().forEach { caveSpot ->
                if(caveSpot !in checked){
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

    private fun List<CaveSpot>.toStartQueue(start: Spot) =
            this.map { caveSpot ->
                when (caveSpot.spot) {
                    start -> caveSpot to 0
                    else -> caveSpot to Int.MAX_VALUE
                }
            }.toMap().toMutableMap()

    private fun CaveSpot.neighbours() =
            caveSpots.filter { caveSpot ->
                caveSpot.spot in this.spot.neighbours()
            }
}

private fun List<String>.toCaveSpots() =
        this.mapIndexed { row, line ->
            line.trim().mapIndexed { col, risk ->
                CaveSpot(spot = Spot(col, row), risk = risk.toString().toInt())
            }
        }.flatten()


data class CaveSpot(val spot: Spot, val risk: Int)

data class Spot(val x: Int, val y: Int) {
    fun neighbours() =  listOf(Spot(x-1,y),Spot(x+1,y),Spot(x,y+1),Spot(x,y-1))
}

class DestinationNotFoundException : Throwable()
