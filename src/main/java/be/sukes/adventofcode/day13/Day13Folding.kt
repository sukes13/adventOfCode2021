package be.sukes.adventofcode.day13

class Manual(input: List<String>) {
    var roster: Roster = input.toRoster()
    val foldings: List<Folding> = input.toFoldings()

    fun solutionOne()= foldings.first()
                                    .doFold(roster)
                                    .numberOfSpots()

    fun fold() {
        roster = foldings.fold(roster) {roster,folding ->
            folding.doFold(roster)
        }
        println(print())
    }

    fun print() = roster.print()
}

data class Spot(val x: Int, val y: Int)

data class Roster(var width:Int, var height:Int, val spots:MutableSet<Spot>){
    fun numberOfSpots() = spots.count()

    fun print(): List<String> {
        val emptyRoster = (0..height).map { (0..width).joinToString("") { "." } }.toMutableList()

        return emptyRoster.mapIndexed { i, line ->
            val spotsOfLine = spots.filter { it.y == i }
            when {
                spotsOfLine.isEmpty() -> line
                else -> line.indices.joinToString("") { index ->
                            if (index in spotsOfLine.map { it.x }) "#" else "."
                        }
            }
        }
    }
}

abstract class Folding(open val line: Int) {
    abstract fun doFold(roster: Roster): Roster
    fun folded(axis: Int) = axis - (axis - this.line) * 2
}
data class HorizontalFold(override val line: Int) : Folding(line) {
    override fun doFold(roster: Roster): Roster{
        roster.spots.groupBy { it.y }
                    .forEach { (line, lineSpots) ->
                        if (line > this.line)
                            lineSpots.forEach { roster.spots.add(Spot(it.x, folded(it.y))) }
                    }
        roster.spots.removeIf { it.y >= this.line }
        roster.height = roster.height.div(2)
        return roster
    }
}

data class VerticalFold(override val line: Int) : Folding(line) {
    override fun doFold(roster: Roster): Roster{
        roster.spots.groupBy { it.x }
                    .forEach { (line, lineSpots) ->
                        if (line > this.line)
                            lineSpots.forEach { roster.spots.add(Spot(folded(it.x), it.y)) }
                    }
        roster.spots.removeIf { it.x >= this.line }
        roster.width = roster.width.div(2) - 1
        return roster
    }
}
//fold along y=7
private fun List<String>.toFoldings() =
        this.filter { it.contains("fold") }
                .map { line ->
                    line.replace("fold along ", "")
                        .split("=")
                        .windowed(2).map { (direction, line) ->
                            when (direction) {
                                "x" -> VerticalFold(line.toInt())
                                else -> HorizontalFold(line.toInt())
                            }
                        }.single()
                }

private fun List<String>.toRoster(): Roster {
    val spots = this.filterNot { it.contains("fold") || it.isBlank() }
                                    .map { line ->
                                        line.split(",")
                                            .windowed(2).map { (x, y) ->
                                                Spot(x.toInt(), y.toInt())
                                            }.single()
                                    }.toMutableSet()
    val width = spots.map { it.x }.max()!!
    val height = spots.map { it.y }.max()!!

    return Roster(width, height,spots)
}