import java.io.File
import kotlin.system.measureTimeMillis

private val scores = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()

private fun getScoreFromStrings(vararg strings: String): Int {
    var current = strings[0].toSet()

    for (string in strings.slice(1..strings.lastIndex)) {
        current = current intersect string.toSet()
    }

    return scores.indexOf(current.first()) + 1
}


private fun part1(): Int {
    var score = 0

    File("src/main/resources/day03.txt").bufferedReader().forEachLine { line ->
        run {
            val first = line.subSequence(0, line.length / 2).toString()
            val second = line.subSequence(line.length / 2, line.length).toString()

            score += getScoreFromStrings(first, second)

        }
    }

    return score
}


private fun part2(): Int {
    var score = 0
    val elves = mutableListOf<String>()

    File("src/main/resources/day03.txt").bufferedReader().forEachLine { line ->
        run {
            if (elves.size < 2) {
                elves.add(line)
            } else {
                elves.add(line)

                score += getScoreFromStrings(*elves.toTypedArray())

                elves.clear()
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
