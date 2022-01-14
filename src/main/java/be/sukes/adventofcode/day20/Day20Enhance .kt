package be.sukes.adventofcode.day20

class Enhancer(input: List<String>) {
    val checkList = input.first().toCheckList()
    var roster = input.toRoster()

    fun solution(turns: Int): Int {
        enhance(turns)
        return roster.numberOfLights()
    }

    fun enhance(turns: Int) =
            (1..turns).forEach { turn ->
                roster = when {
                    isFlipImage() && turn % 2 == 0 -> roster.enhanceFlip(checkList)
                    else -> roster.enhance(checkList)
                }
            }

    private fun isFlipImage() = checkList.first() == 0 && checkList.last() != 511
}

data class Roster(val width: Int, val height: Int, val spots: MutableSet<Spot>) {
    fun numberOfLights() = spots.count()

    fun enhanceFlip(checkList: List<Int>): Roster {
        addDoubleLitBorder()
        return enhance(checkList)
    }

    fun enhance(checkList: List<Int>): Roster {
        val newSpots = (-1..width).map { x ->
            (-1..height).map { y ->
                Spot(x + 1, y + 1) to toDecimal(Spot(x, y), spots)
            }
        }.flatten().toMap().filter { it.value in checkList }.keys.toMutableSet()

        return Roster(width + 2, height + 2, newSpots)
    }

    private fun toDecimal(spot: Spot, spots: MutableSet<Spot>) =
            spot.pixels().map {
                if (it in spots) 1 else 0
            }.joinToString("")
                    .toInt(2)

    private fun addDoubleLitBorder() =
            (-2..width + 1).forEach { x ->
                (-2..height + 1).forEach { y ->
                    if (isOutsideRoster(x, y)) spots.add(Spot(x, y))
                }
            }

    private fun isOutsideRoster(x: Int, y: Int) =
            (x < 0 || y < 0 || x >= width || y >= height)

    fun print(): List<String> {
        val emptyRoster = (0 until height).map {
            (0 until width).joinToString("") { "." }
        }.toMutableList()

        return emptyRoster.mapIndexed { i, line ->
            val spotsOfLine = spots.filter { it.y == i }
            when {
                spotsOfLine.isEmpty() -> line
                else -> line.indices.joinToString("") { x ->
                    if (x in spotsOfLine.map { it.x }) "#" else "."
                }
            }
        }
    }
}

data class Spot(val x: Int, val y: Int) {
    fun pixels() =
            (y - 1..y + 1).map { nY ->
                (x - 1..x + 1).map { nX ->
                    Spot(nX, nY)
                }
            }.flatten()
}

private fun List<String>.toRoster(): Roster {
    val spots = this.asSequence().drop(2)
            .mapIndexed { row, line ->
                line.trim().mapIndexed { col, light ->
                    if (light == '#') Spot(col, row) else null
                }
            }.flatten().filterNotNull().toMutableSet()
    val width = this.drop(2)[0].length
    val height = this.drop(2).size
    return Roster(width, height, spots)
}

private fun String.toCheckList() =
        this.trim().mapIndexed { i, char ->
            if (char == '#') i else null
        }.filterNotNull()


