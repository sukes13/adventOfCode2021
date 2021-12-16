package be.sukes.adventofcode.day6

class BetterFishRecord(fishString: String){
    private val KEYS = (0..8)
    var fishPerAge : MutableMap<Int,Int> = fishString.toFish().toFishPerAge()
    var dayCount = 0

    fun progress(numberOfDays: Int) =
            repeat((0..numberOfDays).drop(1).size) {
                progressDay()

                dayCount += 1
                println("-- day $dayCount -- $fishPerAge")
            }


    fun progressDay() {
        fishPerAge.keys.fold(fishPerAge){ rec, age ->
            when (age) {
                0 -> {
                    rec[8] = rec[8]!! + rec[0]!!
                    rec[6] = rec[6]!! + rec[0]!!
                }
                else -> {
                    rec[age - 1] = rec[age - 1]!! + rec[age]!!
                }
            }
            rec[age] = 0

            return@fold rec
        }
    }

    private fun Sequence<Int>.toFishPerAge() =
        this.toList().fold(createEmptyRecord()){ rec, age ->
            rec[age] = rec[age]!! + 1
            return@fold rec
        }

    private fun createEmptyRecord(): MutableMap<Int, Int> {
        return KEYS.fold(mutableMapOf()){ rec, age ->
            rec[age] = 0
            return@fold rec
        }
    }

    override fun toString(): String {
        return "$fishPerAge"
    }


}

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

    override fun toString(): String = allFish.joinToString(",")

    fun count() = allFish.count()
}
private fun String.toFish() =
        this.split(",")
                .map(String::toInt)
                .asSequence()



