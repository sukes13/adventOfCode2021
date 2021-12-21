package be.sukes.adventofcode.day10

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day10ChunksTest{
    @Test
    fun `isCorruptLine - isCorruptLine - corrupt = empty`() {
        val actual = NavSystem().corruptingCharOf("[({(<(())[]>[[{[]{<()<>>")

        assertThat(actual).isEqualTo("")
    }

    @Test
    fun `isCorruptLine - isCorruptLine - corrupt = brace`() {
        val actual = NavSystem().corruptingCharOf("{([(<{}[<>[]}>{[]{[(<()>")

        assertThat(actual).isEqualTo("}")
    }

    @Test
    fun `isCorruptLine - isCorruptLine - corrupt = parenthesis`() {
        val actual = NavSystem().corruptingCharOf("[[<[([]))<([[{}[[()]]]")

        assertThat(actual).isEqualTo(")")
    }

    @Test
    fun `isCorruptLine - isCorruptLine - corrupt = diamond`() {
        val actual = NavSystem().corruptingCharOf("<{([([[(<>()){}]>(<<{{")

        assertThat(actual).isEqualTo(">")
    }

    @Test
    fun `isCorruptLine - isCorruptLine - corrupt = bracket`() {
        val actual = NavSystem().corruptingCharOf("[{[{({}]{}}([{[{{{}}([]")

        assertThat(actual).isEqualTo("]")
    }

    @Test
    fun `test lines - solutionOne -  26397`() {
        val actual = NavSystem().solutionOne(FileReader().readLines("/day10/testLines.txt"))

        assertThat(actual).isEqualTo(26397)
    }

    @Test
    fun `lines - solutionOne -  413733`() {
        val actual = NavSystem().solutionOne(FileReader().readLines("/day10/lines.txt"))

        assertThat(actual).isEqualTo(413733)
    }

    @Test
    fun `getCompletion - correct completion`() {
        val actual = NavSystem().completionOf("[({(<(())[]>[[{[]{<()<>>")

        assertThat(actual).isEqualTo("}}]])})]")
    }

    @Test
    fun `getCompletion - corrupt line - completion ""`() {
        val actual = NavSystem().completionOf("{([(<{}[<>[]}>{[]{[(<()>")

        assertThat(actual).isEqualTo("")
    }

    @Test
    fun `getCompletionScore - score =  294`() {
        val actual = NavSystem().completionScoreOf("])}>")

        assertThat(actual).isEqualTo(294)
    }

    @Test
    fun `test lines - solutionTwo - score =  288957`() {
        val actual = NavSystem().solutionTwo(FileReader().readLines("/day10/testLines.txt"))

        assertThat(actual).isEqualTo(288957)
    }

    @Test
    fun `lines - solutionTwo - score =  3354640192`() {
        val actual = NavSystem().solutionTwo(FileReader().readLines("/day10/lines.txt"))

        assertThat(actual).isEqualTo(3354640192L)
    }

}

