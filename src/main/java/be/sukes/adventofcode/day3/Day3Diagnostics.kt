package be.sukes.adventofcode.day3

class Day3PowerDiagnostics {
    fun calculateGamma(diag: List<String>): Rate =
            diag.toCountedByte()
                .map {positionInCountByte -> if (positionInCountByte.hasMoreOnes(diag.size)) "1" else "0"}
                .toRate()

    fun calculateEpsilon(diag: List<String>): Rate =
            diag.toCountedByte()
                .map {positionInCountByte -> if (positionInCountByte.hasMoreOnes(diag.size)) "0" else "1"}
                .toRate()

    private fun Int.hasMoreOnes(numberOfBytes: Int): Boolean = this.toDouble().div(numberOfBytes) > 0.5

    fun powerUsage(diag: List<String>): Int =
            calculateGamma(diag).decimal.times(calculateEpsilon(diag).decimal)

}

private fun List<String>.toCountedByte(): List<Int> {
    var countList = List(this.first().length){0}
    this.forEach { byte ->
        countList = byte.chunked(1)
                        .zip(countList) { bit, positionInCountByte ->
                            if (bit == "1") positionInCountByte.plus(1) else positionInCountByte
                         }
    }
    return countList
}

private fun List<String>.toRate(): Rate = Rate(this.joinToString(""))

data class Rate(val binary: String, val decimal : Int = binary.toInt(2))