package be.sukes.adventofcode.day4

class Day4Bingo{

    fun solution1(cardsString: List<String>,drawsString: String) : Int?{
        val result: MutableMap<Int, Pair<Int, Int>> = play(cardsString, drawsString)

        val winningCardAndDraw = result[result.keys.firstOrNull()]
        if (winningCardAndDraw != null) {
            return winningCardAndDraw.first.times(winningCardAndDraw.second)
        }
        return 0
    }

    fun solution2(cardsString: List<String>,drawsString: String) : Int?{
        val result: MutableMap<Int, Pair<Int, Int>> = play(cardsString, drawsString)

        val lastWinningCardAndDraw = result[result.keys.lastOrNull()]
        if (lastWinningCardAndDraw != null) {
            return lastWinningCardAndDraw.first.times(lastWinningCardAndDraw.second)
        }
        return 0
    }

    private fun play(cardsString: List<String>, drawsString: String): MutableMap<Int, Pair<Int, Int>> {
        val cards = cardsString.asBingoCards()
        val draws = parseDraws(drawsString)

        val cardWinningDraw: MutableMap<Int,Pair<Int, Int>> = mutableMapOf()
        draws.forEach { draw ->
            cards.forEach { card ->
                if (card.draw(draw)) cardWinningDraw.putIfAbsent(card.id,Pair(card.unMarkedScore(), draw))
            }
        }
        return cardWinningDraw
    }

    private fun parseDraws(drawsString: String): List<Int> =
            drawsString.split(",").map { it.toInt() }

}

fun List<String>.asBingoCards(): List<BingoCard>  =
        this.filter { it.isNotBlank() }
                .windowed(5,5)
                .map { BingoCard(it) }
