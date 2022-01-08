package be.sukes.adventofcode.day16

import java.math.BigInteger


class PacketDecoder {
    fun solutionOne(transmission: String): Int {
        val decodePacket = unpackTransmission(transmission)
        return decodePacket.allPackets().map { it.version }.sum()
    }

    fun unpackTransmission(transmission: String): Packet {
        val decodePacket = decodePacket(transmission)
        decodePacket.unpack(removeHeaders(transmission))
        return decodePacket
    }

    fun decodePacket(transmission: String): Packet {
        val (version, typeID) = headers(transmission)
        return when (typeID) {
            4 -> ValuePacket(version, typeID)
            else -> OperatorPacket(version, typeID)
        }
    }

    private fun headers(transmission: String) =
            transmission.take(3).toInt(2) to transmission.substring(3, 6).toInt(2)


}

abstract class Packet(open val version: Int, open val typeID: Int) {
    abstract fun unpack(trans: String): String
    abstract fun allPackets(): List<Packet>
}

data class OperatorPacket(override val version: Int, override val typeID: Int) : Packet(version, typeID) {
    val subPackets = mutableListOf<Packet>()

    override fun unpack(trans: String): String {
        val lengthTypeID = trans.take(1)
        if (lengthTypeID == "0") {
            val numberOfBits = trans.substring(1, 16).toInt(2)
            var subsString = trans.substring(16, 16 + numberOfBits - 1)

            while (subsString.isNotBlank()) {
                subsString = addSub(subsString)
            }

            return if(trans.length >= (16 + numberOfBits)) trans.substring(16 + numberOfBits, trans.length) else("")
        } else if (lengthTypeID == "1") {
            var numberOfSubs = trans.substring(1, 12).toInt(2)
            var subsString = trans.substring(12, trans.length)

            while (numberOfSubs > 0) {
                subsString = addSub(subsString)
                numberOfSubs--
            }
            return subsString
        }
        return "not impl"
    }

    private fun addSub(subsString: String): String {
        val newPacket = PacketDecoder().decodePacket(subsString)
        val leftover = newPacket.unpack(removeHeaders(subsString))
        subPackets.add(newPacket)
        return leftover
    }

    override fun allPackets(): List<Packet> {
        return allSubPackets(this.subPackets).plus(this)
    }

    private fun allSubPackets(subs: MutableList<Packet>): List<Packet> {
        return subs.map {
            if (it is ValuePacket) listOf(it)
            else {
                allSubPackets((it as OperatorPacket).subPackets).plus(it)
            }
        }.flatten()
    }

}

data class ValuePacket(override val version: Int, override val typeID: Int) : Packet(version, typeID) {
    var value = ""

    override fun unpack(trans: String): String {
        var keepReading = true
        var leftover = trans

        while (keepReading) {
            val group = leftover.take(5)
                    .also { leftover = leftover.drop(5) }
            keepReading = group.first().toString() == "1"
            value += group.substring(1)
        }

        return leftover
    }

    override fun allPackets(): List<Packet> = listOf(this)
}

fun String.toBinary2(): String {
    val length = this.length * 4
    val binary = BigInteger(this, 16).toString(2)
    return (0 until length - binary.length).joinToString("") { "0" } + binary
}

fun String.toBinary(): String {
    return this.map { hex ->
        when (hex) {
            '0' -> "0000"
            '1' -> "0001"
            '2' -> "0010"
            '3' -> "0011"
            '4' -> "0100"
            '5' -> "0101"
            '6' -> "0110"
            '7' -> "0111"
            '8' -> "1000"
            '9' -> "1001"
            'A' -> "1010"
            'B' -> "1011"
            'C' -> "1100"
            'D' -> "1101"
            'E' -> "1110"
            'F' -> "1111"
            else -> throw InvalidCharacterFoundException()
        }
    }.joinToString("")
}

private fun removeHeaders(input: String) = input.substring(6, input.length)

class InvalidCharacterFoundException : Throwable()
