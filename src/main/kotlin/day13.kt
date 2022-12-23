import java.io.File
import kotlin.math.min
import kotlin.math.sign
import kotlin.system.measureTimeMillis

private fun sign(value: Int): Int {
    return sign(value.toFloat()).toInt()
}

class Packet(string: String) : Comparable<Packet> {
    var value: Int? = null
    var packets = mutableListOf<Packet>()

    init {
        if (string[0] != '[') {
            value = string.toInt()
        } else {
            val data = string.subSequence(1, string.length - 1)
            val stack = ArrayDeque<Char>()
            var currentValue = ""
            for (i in data.indices) {
                when (data[i]) {
                    '[' -> {
                        stack.add('[')
                        currentValue += data[i]
                    }

                    ']' -> {
                        stack.removeLast()
                        currentValue += data[i]
                    }

                    ',' -> {
                        if (stack.size == 0) {
                            packets.add(Packet(currentValue))
                            currentValue = ""
                        } else {
                            currentValue += data[i]
                        }
                    }

                    else -> {
                        currentValue += data[i]
                    }
                }
            }
            if (currentValue.isNotEmpty()) {
                packets.add(Packet(currentValue))
            }
        }
    }

    override fun compareTo(other: Packet): Int {
        if (value != null && other.value != null) {
            return sign(value!! - other.value!!)
        }

        ensureList()
        other.ensureList()

        for (index in 0 until min(packets.size, other.packets.size)) {
            val res = packets[index].compareTo(other.packets[index])

            if (res != 0) return res
        }

        return sign(packets.size - other.packets.size)
    }

    private fun ensureList() {
        if (value != null) {
            packets.add(Packet(value.toString()))
            value = null
        }
    }

    override fun toString(): String {
        if (value != null) return value.toString()

        return "[${packets.joinToString(",") { it.toString() }}]"
    }
}


private fun part1(): Int {
    return File("src/main/resources/day13.txt")
        .bufferedReader()
        .readLines()
        .chunked(3)
        .map { it.subList(0, 2).map { l -> Packet(l) } }
        .mapIndexed { index, (p1, p2) -> if (p1 < p2) index + 1 else 0 }
        .sum()
}

private fun part2(): Int {
    val packets = File("src/main/resources/day13.txt")
        .bufferedReader()
        .readLines()
        .filter { it.isNotEmpty() }
        .map { Packet(it) }
        .toMutableList()

    val dividerOne = Packet("[[2]]")
    val dividerTwo = Packet("[[6]]")

    packets.addAll(listOf(dividerOne, dividerTwo))
    packets.sort()

    return (packets.indexOf(dividerOne) + 1) * (packets.indexOf(dividerTwo) + 1)

}

fun main() {
    val part1Answer: Int
    val part1Time = measureTimeMillis { part1Answer = part1() }
    println("Part one:")
    println("Answer: $part1Answer")
    println("Time: $part1Time")

    val part2Answer: Int
    val part2Time = measureTimeMillis { part2Answer = part2() }
    println("Part two:")
    println("Answer: \n$part2Answer")
    println("Time: $part2Time")
}
