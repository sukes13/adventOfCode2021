package be.sukes.adventofcode.day6

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day6FishTest{

    @Test
    fun `create '3,4,3,1,2' toString = 3,4,3,1,2`() {
        val actual = FishRecord("3,4,3,1,2")

        assertThat("$actual").isEqualTo("3,4,3,1,2")
    }

    @Test
    fun `test fish - add 1 day - fish = 2,3,2,0,1`() {
        val fishRecord = FishRecord("3,4,3,1,2")
        fishRecord.progress(1)

        assertThat("$fishRecord").isEqualTo("2,3,2,0,1")
    }

    @Test
    fun `test fish - add 2 days - fish = 1,2,1,6,0,8`() {
        val fishRecord = FishRecord("3,4,3,1,2")
        fishRecord.progress(2)

        assertThat("$fishRecord").isEqualTo("1,2,1,6,0,8")
    }

    @Test
    fun `test fish - add 10 days - fish = 0,1,0,5,6,0,1,2,2,3,7,8`() {
        val fishRecord = FishRecord("3,4,3,1,2")
        fishRecord.progress(10)

        assertThat("$fishRecord").isEqualTo("0,1,0,5,6,0,1,2,2,3,7,8")
    }

    @Test
    fun `test fish - add 18 days - fish count = 26`() {
        val fishRecord = FishRecord("3,4,3,1,2")
        fishRecord.progress(18)

        assertThat(fishRecord.count()).isEqualTo(26)
    }

    @Test
    fun `test fish - add 80 days - fish count = 5934`() {
        val fishRecord = FishRecord("3,4,3,1,2")
        fishRecord.progress(80)

        assertThat(fishRecord.count()).isEqualTo(5934)
    }

    @Test
    fun `start fish' - add 26 days - fish count = solution`() {
        val fishRecord = FishRecord(FileReader().readLines("/day6/startFish.txt").single())

        fishRecord.progress(80)

        assertThat(fishRecord.count()).isEqualTo(356190)
    }

//    @Test
//    fun `test fish - add 256 days - fish count = 5934`() {
//        val fishRecord = FishRecord("3,4,3,1,2")
//        fishRecord.progress(256)
//
//        assertThat(fishRecord.count()).isEqualTo(26984457539)
//    }
}