package be.sukes.adventofcode.day12

import be.sukes.adventofcode.day11.prepare
import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

val cavesOne = """start-A
                start-b
                A-c
                A-b
                b-d
                A-end
                b-end""".prepare()

class Day12CavesTest {

    @Test
    fun `create CaveRecord - is correct`() {
        val actual = CavesRecord(cavesOne)

        assertThat(actual.caves.toString()).contains("name='start', neighbours=[A, b]",
                                                    "name='A', neighbours=[start, c, b, end]",
                                                    "name='b', neighbours=[start, A, d, end]",
                                                    "name='c', neighbours=[A]",
                                                    "name='d', neighbours=[b]",
                                                    "name='end', neighbours=[A, b]]")
    }

    @Test
    fun `small caves - paths - is correct`() {
        val cavesRecord = CavesRecord(cavesOne)

        val actual = cavesRecord.search()

        assertThat(actual.paths).containsExactlyInAnyOrder("start,A,b,A,c,A,end",
                                                    "start,A,b,A,end",
                                                    "start,A,b,end",
                                                    "start,A,c,A,b,A,end",
                                                    "start,A,c,A,b,end",
                                                    "start,A,c,A,end",
                                                    "start,A,end",
                                                    "start,b,A,c,A,end",
                                                    "start,b,A,end",
                                                    "start,b,end")
    }

    @Test
    fun `test caves - paths - is correct`() {
        val cavesRecord = CavesRecord(FileReader().readLines("/day12/testCaves.txt"))

        val actual = cavesRecord.search()

        assertThat(actual.paths).containsExactlyInAnyOrder(*FileReader().readLines("/day12/testPaths.txt").toTypedArray())
    }

    @Test
    fun `cavesOne - solutionOne - is test 10`() {
        val cavesRecord = CavesRecord(cavesOne)

        val actual = cavesRecord.search().solution()

        assertThat(actual).isEqualTo(10)
    }

    @Test
    fun `test caves - solutionOne - is test 19`() {
        val cavesRecord = CavesRecord(FileReader().readLines("/day12/testCaves.txt"))

        val actual = cavesRecord.search().solution()

        assertThat(actual).isEqualTo(19)
    }

    @Test
    fun `test caves 2 - solutionOne - is test 226`() {
        val cavesRecord = CavesRecord(FileReader().readLines("/day12/testCaves2.txt"))

        val actual = cavesRecord.search().solution()

        assertThat(actual).isEqualTo(226)
    }

    @Test
    fun `caves - solutionOne - is test 4338`() {
        val cavesRecord = CavesRecord(FileReader().readLines("/day12/caves.txt"))

        val actual = cavesRecord.search().solution()

        assertThat(actual).isEqualTo(4338)
    }

    @Test
    fun `cavesOne - version 2 - is correct`() {
        val cavesRecord = CavesRecord(cavesOne)

        val actual = cavesRecord.searchV2()

        assertThat(actual.paths).containsExactlyInAnyOrder(*FileReader().readLines("/day12/cavesOneSolTwo.txt").toTypedArray())
    }

    @Test
    fun `cavesOne - version 2 - solution - 36`() {
        val cavesRecord = CavesRecord(cavesOne)

        val actual = cavesRecord.searchV2().solution()

        assertThat(actual).isEqualTo(36)
    }

    @Test
    fun `test caves - version 2 - solution - 103`() {
        val cavesRecord = CavesRecord(FileReader().readLines("/day12/testCaves.txt"))

        val actual = cavesRecord.searchV2().solution()

        assertThat(actual).isEqualTo(103)
    }

    @Test
    fun `test caves 2 - version 2 - solution - 3509`() {
        val cavesRecord = CavesRecord(FileReader().readLines("/day12/testCaves2.txt"))

        val actual = cavesRecord.searchV2().solution()

        assertThat(actual).isEqualTo(3509)
    }

    @Test
    fun `caves 2 - version 2 - solution - 114189`() {
        val cavesRecord = CavesRecord(FileReader().readLines("/day12/caves.txt"))

        val actual = cavesRecord.searchV2().solution()

        assertThat(actual).isEqualTo(114189)
    }
}


