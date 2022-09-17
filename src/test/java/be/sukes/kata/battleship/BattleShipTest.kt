package be.sukes.kata.battleship

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

typealias Spot = Pair<Int, Int>

class BattleShipTest {
    private val horizontalShip = Ship(Spot(0, 0), Spot(2, 0))
    private val verticalShip = Ship(Spot(0, 0), Spot(0, 2))

    @Test
    fun `When HorizontalShip - parts are at 0,0 and 0,1 and 0,2`() {
        assertThat(horizontalShip.showParts()).containsExactly(
                Part(Spot(0, 0)),
                Part(Spot(1, 0)),
                Part(Spot(2, 0))
        )
    }

    @Test
    fun `When HorizontalShip - shoot around - OK`() {
        assertThat(horizontalShip.shoot(Spot(0, 0))).isEqualTo(true)
        assertThat(horizontalShip.shoot(Spot(1, 0))).isEqualTo(true)
        assertThat(horizontalShip.shoot(Spot(2, 0))).isEqualTo(true)
        assertThat(horizontalShip.shoot(Spot(3, 0))).isEqualTo(false)
        assertThat(horizontalShip.shoot(Spot(1, 1))).isEqualTo(false)
    }

    @Test
    fun `When HorizontalShip - shoot area - hit at 0,0 AND 0,1, 0,2`() {
        listOf(Spot(0, 0), Spot(0, 1), Spot(0, 2), Spot(0, 3),
                Spot(1, 0), Spot(1, 1), Spot(1, 2), Spot(1, 3),
                Spot(2, 0), Spot(2, 1), Spot(2, 2), Spot(2, 3),
                Spot(3, 0), Spot(3, 1), Spot(3, 2), Spot(3, 3))
                .forEach { horizontalShip.shoot(it) }

        assertThat(horizontalShip.showHits()).containsExactly(Spot(0, 0), Spot(1, 0), Spot(2, 0))
    }

    @Test
    fun `When HorizontalShip - shoot whole - wasSunk`() {
        assertThat(horizontalShip.shoot(Spot(0, 0))).isEqualTo(true)
        assertThat(horizontalShip.wasSunk()).isEqualTo(false)
        assertThat(horizontalShip.shoot(Spot(1, 0))).isEqualTo(true)
        assertThat(horizontalShip.wasSunk()).isEqualTo(false)
        assertThat(horizontalShip.shoot(Spot(2, 0))).isEqualTo(true)
        assertThat(horizontalShip.wasSunk()).isEqualTo(true)
    }

    @Test
    fun `When VerticalShip - shoot around - OK`() {
        assertThat(verticalShip.shoot(Spot(0, 0))).isEqualTo(true)
        assertThat(verticalShip.shoot(Spot(0, 1))).isEqualTo(true)
        assertThat(verticalShip.shoot(Spot(0, 2))).isEqualTo(true)
        assertThat(verticalShip.shoot(Spot(0, 4))).isEqualTo(false)
        assertThat(verticalShip.shoot(Spot(3, 0))).isEqualTo(false)
    }

}

data class Part(val spot: Spot, val isHit: Boolean = false)

class Ship(front: Spot, back: Spot) {
    private var parts = toParts(front, back)

    fun showParts() = parts
    fun showHits() = parts.hits()
    fun wasSunk() = parts.allHit()

    fun shoot(spot: Spot): Boolean {
        println("Shot fired at: ${spot.first},${spot.second}")

        parts = parts.update(spot)

        return parts.any { it.spot == spot }
                    .also { if (it) println("Ship was hit!!!") }
    }

    private fun toParts(front: Spot, back: Spot) =
            (front.first..back.first).flatMap { x ->
                (front.second..back.second).map { y ->
                    Part(Spot(x, y))
                }
            }.toSet()
}

private fun Set<Part>.update(spot: Spot) =
        this.map { part ->
            when (part.spot) {
                spot -> part.copy(isHit = true)
                else -> part
            }
        }.toSet()

private fun Set<Part>.allHit() = all { it.isHit }

private fun Set<Part>.hits() = filter { it.isHit }.map { it.spot }
