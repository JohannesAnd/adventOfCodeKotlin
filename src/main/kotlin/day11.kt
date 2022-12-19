import java.io.File
import kotlin.system.measureTimeMillis

class Monkey(private val divideBy: Int) {
    var id = ""
    var items = mutableListOf<Long>()
    var test = 1
    var nextMonkeys = hashMapOf<Boolean, Monkey>()
    var operation: String = ""
    var inspectionCount = 0
    var commonDivisor = 0

    private fun inspectItem() {
        inspectionCount++

        val parts = operation.substring(operation.indexOf("= ")).split(" ").map { it.trim() }

        val operator = parts[2]
        val value2 = parts[3]

        var old = items.removeFirst()

        if (operator == "+") {
            old += if (value2 == "old") old else value2.toLong()
        } else if (operator == "*") {
            old *= if (value2 == "old") old else value2.toLong()
        }

        old /= divideBy

        old %= commonDivisor

        nextMonkeys[(old % test) == 0L]?.items?.add(old)
    }

    fun inspectItems() {
        while (items.isNotEmpty()) {
            inspectItem()
        }
    }
}

private fun runMonkeySimulation(divideBy: Int, rounds: Int): Long {
    val monkeyData = File("src/main/resources/day11.txt").bufferedReader().readLines().chunked(7)

    val monkeys = monkeyData.map { Monkey(divideBy) }

    monkeys.forEachIndexed { index, monkey ->
        run {
            monkey.id = monkeyData[index][0].substring(0, monkeyData[index][0].length - 1)
            monkey.items = monkeyData[index][1].substring(18).split(",").map { it.trim().toLong() }.toMutableList()
            monkey.operation = monkeyData[index][2]
            monkey.test = monkeyData[index][3].substring(21).toInt()

            val trueMonkey = monkeys[monkeyData[index][4].split(" ").last().toInt()]
            val falseMonkey = monkeys[monkeyData[index][5].split(" ").last().toInt()]

            monkey.nextMonkeys[true] = trueMonkey
            monkey.nextMonkeys[false] = falseMonkey
        }
    }

    val commonDivisor = monkeys.fold(1) { product, monkey -> product * monkey.test }

    monkeys.forEach { it.commonDivisor = commonDivisor }

    for (round in 1..rounds) {
        for (monkey in monkeys) {
            monkey.inspectItems()
        }
    }

    return monkeys
        .map { it.inspectionCount.toLong() }
        .sorted()
        .takeLast(2)
        .reduce(Long::times)
}

private fun part1(): Long {
    return runMonkeySimulation(3, 20)
}

private fun part2(): Long {
    return runMonkeySimulation(1, 10_000)
}

fun main() {
    val part1Answer: Long
    val part1Time = measureTimeMillis { part1Answer = part1() }
    println("Part one:")
    println("Answer: $part1Answer")
    println("Time: $part1Time")

    val part2Answer: Long
    val part2Time = measureTimeMillis { part2Answer = part2() }
    println("Part two:")
    println("Answer: \n$part2Answer")
    println("Time: $part2Time")
}
