package be.sukes.adventofcode.day6

class BetterFishRecord(fishString: String){
    private val AGES = (0..8)
    private var fishPerAge : Map<Int,Long> = fishString.toFish()
                                                       .toFishPerAge()
    private var dayCount = 0

    fun progress(numberOfDays: Int) =
        repeat((0..numberOfDays).drop(1).size) {
            progressDay()

            dayCount += 1
            println("-- day $dayCount -- $fishPerAge")
        }

    fun progressDay(){
        fishPerAge = AGES.map { age ->
            when (age) {
                8 -> age to fishPerAge[0]!!
                6 -> age to fishPerAge[7]!! + fishPerAge[0]!!
                else -> age to fishPerAge[age + 1]!!
            }
        }.toMap()
    }

    private fun Sequence<Int>.toFishPerAge() =
        this.toList().fold(createEmptyRecord()){ rec, age ->
            rec[age] = rec[age]!! + 1
            return@fold rec
        }.toMap()

    private fun createEmptyRecord(): MutableMap<Int, Long> =
         AGES.fold(mutableMapOf()){ rec, age ->
            rec[age] = 0L
            return@fold rec
        }

    override fun toString(): String = "$fishPerAge"

    fun count(): Long =
        this.fishPerAge.values.sum()

}

class FishRecord(fishString: String) {
    private var allFish : Sequence<Int> = fishString.toFish()
    private var dayCount = 0

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



