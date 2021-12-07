package be.sukes.adventofcode.day1

class Day1Submarine {

    fun calculateDepthIncrease(sweep : List<String>) : Int =
            sweep.map(String::toInt)
                 .windowed(2)
                 .count {  (x, y) -> x < y }
}
