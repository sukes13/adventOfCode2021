package be.sukes.adventofcode.day16

import be.sukes.adventofcode.import.FileReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class Day16PacketDecoderTest{

    @Test
    fun `test trans - loadTransmission - is OK`() {
        val actual = "D2FE28".toBinary()

        assertThat(actual).isEqualTo("110100101111111000101000")
    }

    @Test
    fun `test trans- unpack value packet - is OK`() {
        val actual = PacketDecoder().unpackTransmission("110100101111111000101000") as ValuePacket

        assertThat(actual).isEqualTo(ValuePacket(6,4))
        assertThat(actual.value.toInt(2)).isEqualTo(2021)
    }

    @Test
    fun `test trans 2 - unpack operator packet - is OK`() {
        val actual = PacketDecoder().unpackTransmission("00111000000000000110111101000101001010010001001000000000") as OperatorPacket

        assertThat(actual).isEqualTo(OperatorPacket(1,6))
        assertThat(actual.subPackets).hasSize(2)
        assertThat(actual.subPackets).containsExactlyInAnyOrder(ValuePacket(6,4),ValuePacket(2,4))
    }

    @Test
    fun `test trans 3 - unpack operator packet - is OK`() {
        val actual = PacketDecoder().unpackTransmission("11101110000000001101010000001100100000100011000001100000") as OperatorPacket

        assertThat(actual).isEqualTo(OperatorPacket(7,3))
        assertThat(actual.subPackets).hasSize(3)
        assertThat(actual.subPackets).containsExactlyInAnyOrder(ValuePacket(2,4),ValuePacket(4,4),ValuePacket(1,4))
    }

    @Test
    fun `test trans 4 - unpack operator packet - is OK`() {
        val actual = PacketDecoder().unpackTransmission("8A004A801A8002F478".toBinary()) as OperatorPacket

        assertThat(actual).isEqualTo(OperatorPacket(4,2))
        val subs = actual.subPackets
        assertThat(subs).hasSize(1)
        assertThat(subs).containsExactlyInAnyOrder(OperatorPacket(1,2))
        val subs2 = (subs[0] as OperatorPacket).subPackets
        assertThat(subs2).hasSize(1)
        assertThat(subs2).containsExactlyInAnyOrder(OperatorPacket(5,2))
        val subs3 =  (subs2[0] as OperatorPacket).subPackets
        assertThat(subs3).hasSize(1)
        assertThat(subs3).containsExactlyInAnyOrder(ValuePacket(6,4))
    }


    @ParameterizedTest(name = "Trans:  \"{0}\" has versionSum = \"{1}\"")
    @MethodSource("testTransmissions")
    fun `test transmission - solutionOne`(transmission: String, result : Int) {
        val actual = PacketDecoder().solutionOne(transmission.toBinary())

        assertThat(actual).isEqualTo(result)
    }

    companion object {
        @JvmStatic
        fun testTransmissions() = listOf(
                Arguments.of("620080001611562C8802118E34", 12),
                Arguments.of("8A004A801A8002F478", 16),
                Arguments.of("C0015000016115A2E0802F182340", 23),
                Arguments.of("A0016C880162017C3686B18A3D4780", 31),
                Arguments.of(FileReader().readLines("/day16/transmission.txt").single(),897)
        )
    }
}


