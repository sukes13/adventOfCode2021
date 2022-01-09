package be.sukes.adventofcode.day17

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Day17ProbeTest {

    private val probeShooter = ProbeShooter("target area: x=20..30, y=-10..-5")

    @Test
    fun `create ProbeShooter - correct target`() {
        assertThat(probeShooter.target).hasSize(66)
        assertThat(probeShooter.edge).isEqualTo(30 to -10 )
    }

    @Test
    fun `shoot - correct start probe`() {
        val actual = Probe(velocity = Velocity(1,-1))

        assertThat(actual).isEqualTo(probeOf(0 to 0, 1 to -1))
    }

    @Test
    fun `shoot positive - 2 steps - correct start probe`() {
        val probe = Probe(velocity = Velocity(5,5))
        val actual = probe.nextStep()
        val actual2 = probe.nextStep().nextStep()

        assertThat(actual).isEqualTo(probeOf(5 to 5, 4 to 4))
        assertThat(actual2).isEqualTo(probeOf(9 to 9, 3 to 3))
    }

    @Test
    fun `shoot negative - 2 steps - correct start probe`() {
        val probe = Probe(velocity = Velocity(5,-5))
        val actual = probe.nextStep()
        val actual2 = probe.nextStep().nextStep()

        assertThat(actual).isEqualTo(probeOf(5 to -5, 4 to -6))
        assertThat(actual2).isEqualTo(probeOf(9 to -11, 3 to -7))
    }

    @Test
    fun `shoot zero - 2 steps - correct start probe`() {
        val probe = Probe(velocity = Velocity(0,0))
        val actual = probe.nextStep()
        val actual2 = probe.nextStep().nextStep()

        assertThat(actual).isEqualTo(probeOf(0 to 0, 0 to -1))
        assertThat(actual2).isEqualTo(probeOf(0 to -1, 0 to -2))
    }

    @ParameterizedTest(name = "Shoot:  \"{0}\" shot = \"{1}\"")
    @MethodSource("testShots")
    fun `shootToTarget - correct`(velocity:Velocity, peak: Int) {
        val actual = probeShooter.shootToTarget(velocity)

        assertThat(actual).isEqualTo(peak)
    }

    companion object {
        @JvmStatic
        fun testShots() = listOf(
                Arguments.of(Velocity(7,2), 3),
                Arguments.of(Velocity(9,0), 0),
                Arguments.of(Velocity(6,3), 6),
                Arguments.of(Velocity(6,9), 45)
        )
    }

    @Test
    fun `shootToTarget - miss`() {
        val actual = probeShooter.shootToTarget(Velocity(17,-4))

        assertThat(actual).isNull()
    }

    @Test
    fun `test solutionOne - peak = 45`() {
        val actual = probeShooter.solutionOne()

        assertThat(actual).isEqualTo(45)
    }

    @Test
    fun `solutionOne - peak = 13203`() {
        val actual = ProbeShooter("target area: x=85..145, y=-163..-108").solutionOne()

        assertThat(actual).isEqualTo(13203)
    }

    @Test
    fun `test solutionTwo - totalHits = 112`() {
        val actual = probeShooter.solutionTwo()

        assertThat(actual).isEqualTo(112)
    }

    @Test
    fun `solutionTwo - totalHits = 5644`() {
        val actual = ProbeShooter("target area: x=85..145, y=-163..-108").solutionTwo()

        assertThat(actual).isEqualTo(5644)
    }

    private fun probeOf(spot: Pair<Int,Int>, velocity: Pair<Int,Int>) = Probe(Spot(spot.first, spot.second), Velocity(velocity.first,velocity.second))
}




