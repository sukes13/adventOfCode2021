package be.sukes.adventofcode.day3

class Day3PowerDiagnostics {
    fun powerUsage(diag: List<String>) =
            calculateGamma(diag).decimal.times(calculateEpsilon(diag).decimal)

    fun lifeSupport(diag: List<String>) =
            findOxygenGenerator(diag).decimal.times(findCO2Scrubber(diag).decimal)

    fun calculateGamma(diag: List<String>) =
            diag.toCountedByte()
                .map {positionInCountByte -> positionInCountByte.mostCommonValue(diag.size) }
                .toRate()

    fun calculateEpsilon(diag: List<String>) =
            diag.toCountedByte()
                .map {positionInCountByte -> positionInCountByte.leastCommonValue(diag.size)}
                .toRate()

    fun findOxygenGenerator(diag: List<String>) =
            findMatchingByte(diag) { numberOfBytes -> this.mostCommonValue(numberOfBytes)}

    fun findCO2Scrubber(diag: List<String>) =
            findMatchingByte(diag) { numberOfBytes -> this.leastCommonValue(numberOfBytes)}


    private fun findMatchingByte(diag: List<String>, preference: Int.(size: Int) -> String): Rate {
        var matchingBytes = diag
        var i = 0
        while(matchingBytes.size > 1){
            val preferenceAtIndex = matchingBytes.toCountedByte()[i].preference(matchingBytes.size)
            matchingBytes = matchingBytes.filter {byte ->
                byte.chunked(1)[i] == preferenceAtIndex
            }
            i++
        }
        return Rate(matchingBytes.first())
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

    private fun Int.leastCommonValue(numberOfBytes: Int) =
            if (hasMoreOnes(numberOfBytes)) "0" else "1"

    private fun Int.mostCommonValue(numberOfBytes: Int) =
            if (hasMoreOnes(numberOfBytes)) "1" else "0"

    private fun Int.hasMoreOnes(numberOfBytes: Int) =
            this.toDouble().div(numberOfBytes) >= 0.5

    private fun List<String>.toRate(): Rate = Rate(this.joinToString(""))

}

data class Rate(val binary: String, val decimal : Int = binary.toInt(2))