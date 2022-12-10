import java.io.File
import kotlin.system.measureTimeMillis

private fun getRanges(string: String): Array<IntRange> {
    val numbers = string.split('-', ',').map { it.toInt() }

    return arrayOf(IntRange(numbers[0], numbers[1]), IntRange(numbers[2], numbers[3]))
}

private fun isOverlapping(ranges: Array<IntRange>): Boolean {
    return (ranges[0] intersect ranges[1]).isNotEmpty()
}

private fun isCompleteOverlap(ranges: Array<IntRange>): Boolean {
    return (ranges[0] intersect ranges[1]).size == minOf(ranges.minOf { it.count() })
}

private fun part1(): Int {
    var score = 0

    File("src/main/resources/day04.txt").bufferedReader().forEachLine { line ->
        run {
            if (isCompleteOverlap(getRanges(line))) {
                score++
            }
        }
    }

    return score
}


private fun part2(): Int {
    var score = 0

    File("src/main/resources/day04.txt").bufferedReader().forEachLine { line ->
        run {
            if (isOverlapping(getRanges(line))) {
                score++
            }
        }
    }

    return score
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
