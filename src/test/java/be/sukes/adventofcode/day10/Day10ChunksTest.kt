package be.sukes.adventofcode.day10

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day10ChunksTest{
    @Test
    fun `isCorruptLine - line 1 - true`() {
        val actual = NavSystem().isCorruptLine("[({(<(())[]>[[{[]{<()<>>")

        assertThat(actual).isBlank()
    }

    @Test
    fun `isCorruptLine - line 4 - true`() {
        val actual = NavSystem().isCorruptLine("{([(<{}[<>[]}>{[]{[(<()>")

        assertThat(actual).isEqualTo("}")
    }


}

