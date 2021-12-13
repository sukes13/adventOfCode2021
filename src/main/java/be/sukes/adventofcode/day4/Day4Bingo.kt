package be.sukes.adventofcode.day4

class Day4Bingo{

    fun solution1(cardsString: List<String>,drawsString: String) : Int =
            cardsString.play(drawsString).toSolution { this.keys.first()}

    fun solution2(cardsString: List<String>,drawsString: String) : Int =
            cardsString.play(drawsString).toSolution { this.keys.last()}

    private fun MutableMap<Int, Pair<Int, Int>>.toSolution(preference: MutableMap<Int, Pair<Int, Int>>.() -> Int) =
         this[this.preference()]?.calculateSolution() ?: 0

    private fun parseDraws(drawsString: String): List<Int> =
            drawsString.split(",")
                       .map { it.toInt() }

    private fun Pair<Int,Int>.calculateSolution(): Int =
            this.first.times(this.second)

    private fun List<String>.play(drawsString: String): MutableMap<Int, Pair<Int, Int>> {
        val cards = this.asBingoCards()
        val draws = parseDraws(drawsString)

        val cardWinningDraw: MutableMap<Int,Pair<Int, Int>> = mutableMapOf()
        draws.forEach { draw ->
            cards.forEach { card ->
                if (card.draw(draw)) cardWinningDraw.putIfAbsent(card.id,Pair(card.unMarkedScore(), draw))
            }
        }
        return cardWinningDraw
    }
}

fun List<String>.asBingoCards(): List<BingoCard>  =
        this.filter { it.isNotBlank() }
            .windowed(5,5)
            .map { BingoCard(it) }
