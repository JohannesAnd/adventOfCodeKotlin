import java.io.File
import kotlin.system.measureTimeMillis

private fun getInventories(): List<Int> {
    val inventories = mutableListOf(0)

    File("src/main/resources/day01.txt").bufferedReader().forEachLine { line ->
        run {
            if (line.isEmpty()) {
                inventories.add(0)
            } else {
                inventories[inventories.lastIndex] += line.toInt()
            }
        }
    }

    return inventories
}

private fun part1(): Int {
    return getInventories().maxOrNull() ?: 0
}

private fun part2(): Int {
    return getInventories().sorted().takeLast(3).sum()
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
