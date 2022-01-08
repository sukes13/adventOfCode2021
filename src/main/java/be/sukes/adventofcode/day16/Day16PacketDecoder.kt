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
            transmission.take(3).toInt(2) to transmission.toDecimal(3, 6)
}

abstract class Packet(open val version: Int, open val typeID: Int) {
    abstract fun unpack(trans: String): String
    abstract fun allPackets(): List<Packet>
}

data class OperatorPacket(override val version: Int, override val typeID: Int) : Packet(version, typeID) {
    val subPackets = mutableListOf<Packet>()

    override fun unpack(trans: String) =
            when (trans.take(1)) {
                "0" -> handleTotalLength(trans)
                "1" -> handleNumberOfPackets(trans)
                else -> "not impl"
            }

    private fun handleNumberOfPackets(trans: String): String {
        var numberOfSubs = trans.toDecimal(1, 12)
        var subsString = trans.substring(12, trans.length)

        while (numberOfSubs > 0) {
            subsString = addSubPacket(subsString)
            numberOfSubs--
        }
        return subsString
    }

    private fun handleTotalLength(trans: String): String {
        val numberOfBits = trans.toDecimal(1, 16)
        var subsString = trans.substring(16, 15 + numberOfBits)

        while (subsString.isNotBlank()) {
            subsString = addSubPacket(subsString)
        }

        return if (trans.length >= 16 + numberOfBits) trans.substring(16 + numberOfBits, trans.length) else ("")
    }

    private fun addSubPacket(subsString: String): String {
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

fun String.toBinary(): String {
    val length = this.length * 4
    val binary = BigInteger(this, 16).toString(2)
    return (0 until length - binary.length).joinToString("") { "0" } + binary
}

private fun String.toDecimal(start: Int, end: Int) = this.substring(start, end).toInt(2)
private fun removeHeaders(input: String) = input.substring(6, input.length)

