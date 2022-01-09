package be.sukes.adventofcode.day16

import java.math.BigInteger


class PacketDecoder {
    fun solutionOne(transmission: String) =
            unpackTransmission(transmission).allPackets()
                    .map { it.version }
                    .sum()

    fun solutionTwo(transmission: String) =
            unpackTransmission(transmission).value

    fun unpackTransmission(transmission: String): Packet {
        val outerPacket = decodePacket(transmission)
        outerPacket.unpack(removeHeaders(transmission))
        return outerPacket
    }

    companion object {
        fun decodePacket(transmission: String): Packet {
            val (version, typeID) = headers(transmission)
            return when (typeID) {
                4 -> ValuePacket(version, typeID)
                else -> OperatorPacket(version, typeID)
            }
        }
        private fun headers(transmission: String) =
                transmission.take(3).toInt(2) to transmission.toDecimal(3, 6)
    }
}

abstract class Packet(open val version: Int, open val typeID: Int) {
    var value = 0L
    abstract fun unpack(trans: String): String
    abstract fun allPackets(): List<Packet>
}

data class ValuePacket(override val version: Int, override val typeID: Int) : Packet(version, typeID) {
    override fun unpack(trans: String): String {
        var keepReading = true
        var leftover = trans
        var binary = ""

        while (keepReading) {
            val group = leftover.take(5)
            keepReading = group.first().toString() == "1"
            binary += group.substring(1)
            leftover = leftover.drop(5)
        }

        value = binary.toLong(2)
        return leftover
    }

    override fun allPackets(): List<Packet> = listOf(this)
}

data class OperatorPacket(override val version: Int, override val typeID: Int) : Packet(version, typeID) {
    val subPackets = mutableListOf<Packet>()

    override fun unpack(trans: String) =
            when (trans.take(1)) {
                "0" -> unpackLengthInBits(trans)
                "1" -> unpackNumberOfPackets(trans)
                else -> "not impl"
            }

    override fun allPackets() = allSubPackets(this.subPackets).plus(this)

    private fun unpackLengthInBits(trans: String): String {
        val lengthInBits = trans.toDecimal(1, 16)
        var subsString = trans.substring(16, 16 + lengthInBits)

        while (subsString.isNotBlank()) {
            subsString = addSubPacket(subsString)
        }

        calculateValue()
        return if (trans.length >= 16 + lengthInBits) trans.substring(16 + lengthInBits, trans.length) else ("")
    }

    private fun unpackNumberOfPackets(trans: String): String {
        var numberOfSubs = trans.toDecimal(1, 12)
        var subsString = trans.substring(12, trans.length)

        while (numberOfSubs > 0) {
            subsString = addSubPacket(subsString)
            numberOfSubs--
        }

        calculateValue()
        return subsString
    }

    private fun calculateValue() {
        val values = subPackets.map { it.value }
        value = when (typeID) {
            0 -> values.sum()
            1 -> values.reduce { acc, i -> acc * i }
            2 -> values.min()!!
            3 -> values.max()!!
            5 -> values.windowed(2).map { (a, b) -> if (a > b) 1L else 0L }.single()
            6 -> values.windowed(2).map { (a, b) -> if (a < b) 1L else 0L }.single()
            7 -> values.windowed(2).map { (a, b) -> if (a == b) 1L else 0L }.single()
            else -> 666
        }
    }

    private fun addSubPacket(subsString: String): String {
        val newPacket = PacketDecoder.decodePacket(subsString)
        val leftover = newPacket.unpack(removeHeaders(subsString))
        subPackets.add(newPacket)
        return leftover
    }

    private fun allSubPackets(subs: MutableList<Packet>): List<Packet> {
        return subs.map {packet ->
            when (packet) {
                is ValuePacket -> listOf(packet)
                else -> allSubPackets((packet as OperatorPacket).subPackets).plus(packet)
            }
        }.flatten()
    }
}

fun String.toBinary(): String {
    val length = this.length * 4
    val binary = BigInteger(this, 16).toString(2)
    return (0 until length - binary.length).joinToString("") { "0" } + binary
}

private fun String.toDecimal(start: Int, end: Int) = this.substring(start, end).toInt(2)
private fun removeHeaders(input: String) = input.substring(6, input.length)

