package be.sukes.adventofcode.day7

import kotlin.math.absoluteValue

class CrabsRecord(crabsString: String) {
    private val crabs : IntArray = crabsString.toIntArray()

    fun solution1(): Pair<Int,Int> =
            getPosition { fuelCost(it) }

    fun solution2(): Pair<Int,Int> =
            getPosition { correctFuelCost(it) }

    fun fuelCost(position: Int) =
            crabs.map { it.distanceTo(position) }
                 .sum()

    private fun Int.distanceTo(position: Int) =
            (this - position).absoluteValue

    private fun getPosition(fuelCostCalculator: (Int) -> Int): Pair<Int, Int> {
        val range : IntRange = (crabs.min()!! .. crabs.max()!!)
        var bestSpot = crabs.min()!! to fuelCostCalculator(crabs.max()!!)

        range.forEach {pos ->
            val fuelCostTo = fuelCostCalculator(pos)
            bestSpot = when {
                bestSpot.second > fuelCostTo ->  pos to fuelCostTo
                else -> bestSpot
            }
        }
        return bestSpot
    }

    fun correctFuelCost(position: Int) =
            crabs.map { it.correctFuelCostTo(position) }
                 .sum()

    private fun Int.correctFuelCostTo(position: Int) =
        (0 .. this.distanceTo(position)).map { it + 1 }
                                           .sum()

    private fun String.toIntArray() =
            this.split(",")
                .map(String::toInt)
                .toIntArray()

    override fun toString(): String {
        return crabs.joinToString(",")
    }


}
