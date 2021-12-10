package be.sukes.adventofcode.day3

class Day3PowerDiagnostics {
    fun calculateGamma(diag: List<String>): Rate =
        countBit(diag).map { if (moreOnes(it, diag)) "1" else "0"}
                      .toRate()

    fun calculateEpsilon(diag: List<String>): Rate =
        countBit(diag).map {if (moreOnes(it, diag)) "0" else "1"}
                      .toRate()

    private fun moreOnes(it: Int, diag: List<String>) = it.toDouble().div(diag.size) > 0.5

    fun powerUsage(diag: List<String>): Int =
            calculateGamma(diag).decimal.times(calculateEpsilon(diag).decimal)

    private fun countBit(diag: List<String>): List<Int> {
        var countList: List<Int> = List(diag.first().length) { index -> 0}
        diag.forEach { bit ->
            countList = bit.chunked(1).zip(countList) { digit, col ->
                if (digit == "1") col.plus(1) else col
            }
        }
        return countList
    }
}

private fun <E> List<E>.toRate(): Rate = Rate(this.joinToString(""))

data class Rate(val binary: String, val decimal : Int = binary.toInt(2))