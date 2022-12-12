import java.io.File
import java.lang.Exception
import kotlin.system.measureTimeMillis

private fun getMarkerIndex(size: Int): Int {
    val line = File("src/main/resources/day06.txt").bufferedReader().readLine()

    for (index in size..line.length) {
        val chars = line.subSequence(index - size, index)

        if (chars.toSet().size == size) {
            return index
        }
    }

    throw Exception("No marker found")
}

private fun part1(): Int {
    return getMarkerIndex(4)
}

private fun part2(): Int {
    return getMarkerIndex(14)
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
    println("Answer: $part2Answer")
    println("Time: $part2Time")
}
