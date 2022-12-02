import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.system.measureTimeMillis

private enum class Hand {
    A, B, C
}

private enum class Outcome {
    X, Y, Z
}

private val map = mapOf(Outcome.X to Hand.A, Outcome.Y to Hand.B, Outcome.Z to Hand.C)
private val handValues = Hand.values()

private val placementScore = mapOf(1 to 6, 0 to 3, -1 to 0)
private val handScore = mapOf(Hand.A to 1, Hand.B to 2, Hand.C to 3)

private fun getScore(theirHand: Hand, yourHand: Hand): Int {
    val diff = yourHand.ordinal - theirHand.ordinal

    // If the difference is 2, it is the same as -1 the other way around the array
    val win = if (diff.absoluteValue == 2) {
        -1 * diff.sign
    } else {
        diff.sign
    }

    return (placementScore[win] ?: 0) + (handScore[yourHand] ?: 0)
}


private fun part1(): Int {
    var score = 0

    File("src/main/resources/day02.txt").bufferedReader().forEachLine { line ->
        run {
            val (theirHandChar, yourHandChar) = line.split(' ')

            val theirHand = enumValueOf<Hand>(theirHandChar)
            val yourHand = map[enumValueOf(yourHandChar)]

            score += getScore(theirHand, yourHand ?: Hand.A)
        }
    }

    return score
}

private val outcomeMap = mapOf(Outcome.X to -1, Outcome.Y to 0, Outcome.Z to 1)

private fun part2(): Int {
    var score = 0

    File("src/main/resources/day02.txt").bufferedReader().forEachLine { line ->
        run {
            val (theirHandChar, expectedOutcomeChar) = line.split(' ')

            val theirHand = enumValueOf<Hand>(theirHandChar)
            // Offset the index so that you either win/loose/draw
            val yourIndex = theirHand.ordinal + (outcomeMap[enumValueOf(expectedOutcomeChar)] ?: 0)
            // Find our what hand that is and make sure to keep in bounds of the array
            val yourHand = handValues[(yourIndex + handValues.size) % handValues.size]

            score += getScore(theirHand, yourHand)
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
