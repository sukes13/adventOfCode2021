package be.sukes.adventofcode.day17

import kotlin.math.abs

class ProbeShooter(targetStr: String) {
    val target = toTarget(targetStr)
    val edge = targetStr.toEdge()

    fun solutionOne() =
            scanShots(edge).max()!!

    fun solutionTwo() =
            scanShots(edge).count()

    private fun scanShots(edge: Pair<Int, Int>) =
            (0..edge.first).map { x ->
                (-abs(edge.second)..abs(edge.second)).mapNotNull { y ->
                    shootToTarget(Velocity(x, y))
                }
            }.flatten()

    fun shootToTarget(velocity: Velocity) =
            doSteps(Probe(velocity = velocity))

    private tailrec fun doSteps(probe: Probe, peak: Int = 0): Int? {
        val nextProbe = probe.nextStep()
        val newPeak = nextProbe.spot.isHigher(peak)
        return when {
            nextProbe.spot in target -> newPeak
            nextProbe.probeOutOfBounds(edge) -> null
            else -> doSteps(nextProbe, newPeak)
        }
    }

    private fun toTarget(targetStr: String) =
            targetStr.toRangeStrings()
                    .map { (a, b) ->
                        a.toRange().map { x ->
                            b.toRange().map { y ->
                                Spot(x, y)
                            }
                        }.flatten()
                    }.flatten()

    private fun String.toEdge() =
            this.toRangeStrings()
                    .map { (a, b) ->
                        a.split("..")[1].toInt() to b.split("..")[0].toInt()
                    }.first()

    private fun String.toRangeStrings() =
            this.replace("target area: x=", "")
                    .split(", y=")
                    .windowed(2)

    private fun String.toRange() =
            this.split("..")
                    .zipWithNext()
                    .map { (x1, x2) -> x1.toInt() .. x2.toInt() }
                    .single()

}

data class Probe(val spot: Spot = Spot(), val velocity: Velocity) {
    fun nextStep() =
            copy(spot = spot.nextStep(velocity), velocity = velocity.nextStep())

    fun probeOutOfBounds(edge: Pair<Int, Int>) =
            spot.x > edge.first || spot.y < edge.second
}

data class Spot(val x: Int = 0, val y: Int = 0) {
    fun nextStep(velocity: Velocity) =
            Spot(x + velocity.x, y + velocity.y)

    fun isHigher(peak: Int) =
            if (peak < y) y else peak
}

data class Velocity(val x: Int, val y: Int) {
    fun nextStep() =
            Velocity(nextX(), y - 1)

    private fun nextX() =
            when {
                x < 0 -> x + 1
                x == 0 -> 0
                else -> x - 1
            }
}

