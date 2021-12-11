package be.sukes.adventofcode.day3

class Day3PowerDiagnostics {
    fun calculateGamma(diag: List<String>): Rate =
            diag.toCountedByte()
                .map {positionInCountByte -> positionInCountByte.mostCommonValue(diag.size) }
                .toRate()

    fun calculateEpsilon(diag: List<String>): Rate =
            diag.toCountedByte()
                .map {positionInCountByte -> positionInCountByte.leastCommonValue(diag.size)}
                .toRate()

    private fun Int.mostCommonValue(numberOfBytes: Int) =
            if (this.toDouble().div(numberOfBytes) >= 0.5) "1" else "0"

    private fun Int.leastCommonValue(numberOfBytes: Int) =
            if (this.toDouble().div(numberOfBytes) > 0.5) "0" else "1"

    fun powerUsage(diag: List<String>): Int =
            calculateGamma(diag).decimal.times(calculateEpsilon(diag).decimal)

    fun findOxygenGenerator(diag: List<String>): Rate {
        var matchingBytes = diag
        var i = 0
        while(matchingBytes.size > 1){
            val mostCommonAtIndex = matchingBytes.toCountedByte()[i]
                                                        .mostCommonValue(matchingBytes.size)
            matchingBytes = matchingBytes.filter {
                byte -> byte.chunked(1)[i].equals(mostCommonAtIndex)
            }
            i++
        }
        return Rate(matchingBytes.first())
    }

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