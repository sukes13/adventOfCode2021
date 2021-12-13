package be.sukes.adventofcode.day4

class Day4Bingo{

    fun play(cardsString: List<String>,drawsString: String) : Int?{
        val cards = cardsString.asBingoCards()
        val draws = parseDraws(drawsString)

        val cardWinningDraw : MutableList<Pair<Int,Int>> = mutableListOf()
        draws.forEach {draw ->
            cards.forEach {card ->
                if(card.draw(draw)) cardWinningDraw.add(Pair(card.unMarkedScore(), draw))
            }
        }

        val winningPair = cardWinningDraw.first()
        return winningPair.first.times(winningPair.second)

    }

    private fun parseDraws(drawsString: String): List<Int> =
            drawsString.split(",").map { it.toInt() }

}

fun List<String>.asBingoCards(): List<BingoCard>  =
        this.filter { it.isNotBlank() }
                .windowed(5,5)
                .map { BingoCard(it) }
