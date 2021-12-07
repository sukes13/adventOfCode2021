package be.sukes.adventofcode.import

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FileReaderTest {

    @Test
    fun `get file then file exists`() {
        val actual : List<String> = FileReader().readLines("/depthSweep.txt")

        assertThat(actual).contains("171")
    }
}