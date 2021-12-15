package be.sukes.adventofcode.day6

class FishRecord(fishString: String) {
    var allFish : Sequence<Int> = fishString.toFish()
    var dayCount = 0

    fun progress(numberOfDays: Int) =
            repeat((0..numberOfDays).drop(1).size) {
                progressDay()

                dayCount += 1
                println("-- day $dayCount -- ${count()}")
            }

    private fun progressDay() {
        val birthingFish = allFish.count { it == 0 }
        allFish = allFish.addDay()
                         .addNewborns(birthingFish)
    }

    private fun Sequence<Int>.addDay(): Sequence<Int> {
        return this.map { if(it == 0) 6 else it.minus(1) }
    }


    private fun Sequence<Int>.addNewborns(birthingFish : Int) : Sequence<Int> {
        return this.plus((0 .. birthingFish).map { 8 }.drop(1))
    }

    private fun String.toFish() =
            this.split(",")
                .map(String::toInt)
                .asSequence()

    override fun toString(): String = allFish.joinToString(",")
    fun count() = allFish.count()
}



