package be.sukes.adventofcode.day3

class Day3PowerDiagnostics {
    fun calculateGamma(diag: List<String>): Rate =
            diag.toCountedBit()
                .map { if (hasMoreOnes(it, diag.size)) "1" else "0"}
                .toRate()

    fun calculateEpsilon(diag: List<String>): Rate =
            diag.toCountedBit()
                .map {if (hasMoreOnes(it, diag.size)) "0" else "1"}
                .toRate()

    private fun hasMoreOnes(it: Int, diag: Int) = it.toDouble().div(diag) > 0.5

    fun powerUsage(diag: List<String>): Int =
            calculateGamma(diag).decimal.times(calculateEpsilon(diag).decimal)

}

private fun List<String>.toCountedBit(): List<Int> {
    var countList: List<Int> = List(this.first().length) { index -> 0}
    this.forEach { bit ->
        countList = bit.chunked(1).zip(countList) { digit, col ->
            if (digit == "1") col.plus(1) else col
        }
    }
    return countList
}

private fun List<String>.toRate(): Rate = Rate(this.joinToString(""))

data class Rate(val binary: String, val decimal : Int = binary.toInt(2))