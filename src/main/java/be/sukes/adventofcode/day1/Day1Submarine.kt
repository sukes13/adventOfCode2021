package be.sukes.adventofcode.day1

class Day1Submarine {

    fun calculateDepthIncrease(sweep : List<String>) : Int =
            sweep.map(String::toInt)
                 .windowed(2)
                 .count{  (x, y) -> x.isSmaller(y) }

    fun calculateDepthIncreaseBundled(sweep : List<String>) : Int =
            sweep.asSequence().map(String::toInt)
                    .windowed(3)
                        .map{ (x, y, z) -> sumOf(x, y, z) }
                    .windowed(2)
                        .count{  (x, y) -> x.isSmaller(y) }

    private fun Int.isSmaller(y: Int) = this < y
    private fun sumOf(x: Int, y: Int, z: Int) = x + y + z

}


