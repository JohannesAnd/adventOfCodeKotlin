import java.io.File
import kotlin.system.measureTimeMillis


private fun part1(): String {
    val stackData = arrayListOf<List<Char>>()
    var stacks = arrayOf<ArrayDeque<Char>>()

    File("src/main/resources/day05.txt").bufferedReader().forEachLine { line ->
        run {
            if (line.contains('[')) {
                stackData.add(line.chunked(4).map { it[1] })
            }

            if (line.isEmpty()) {
                stacks = Array(stackData[stackData.lastIndex].size) { ArrayDeque() }

                stackData.reversed().forEach {
                    it.forEachIndexed { index, char ->
                        run {
                            if (char != ' ') {
                                stacks[index].add(char)
                            }
                        }
                    }
                }
            }

            if (line.startsWith("move")) {
                val instructions = line.split(' ')
                val count = instructions[1].toInt()
                val from = instructions[3].toInt() - 1
                val to = instructions[5].toInt() - 1

                for (i in 0 until count) {
                    stacks[to].add(stacks[from].removeLast())
                }
            }
        }
    }

    return stacks.map { it.last() }.joinToString("")
}


private fun part2(): String {
    val stackData = arrayListOf<List<Char>>()
    var stacks = arrayOf<ArrayDeque<Char>>()

    File("src/main/resources/day05.txt").bufferedReader().forEachLine { line ->
        run {
            if (line.contains('[')) {
                stackData.add(line.chunked(4).map { it[1] })
            }

            if (line.isEmpty()) {
                stacks = Array(stackData[stackData.lastIndex].size) { ArrayDeque() }

                stackData.reversed().forEach {
                    it.forEachIndexed { index, char ->
                        run {
                            if (char != ' ') {
                                stacks[index].add(char)
                            }
                        }
                    }
                }
            }

            if (line.startsWith("move")) {
                val instructions = line.split(' ')
                val count = instructions[1].toInt()
                val from = instructions[3].toInt() - 1
                val to = instructions[5].toInt() - 1

                val temp = arrayListOf<Char>()

                for (i in 0 until count) {
                    temp.add(stacks[from].removeLast())
                }

                temp.reversed().forEach { char -> stacks[to].add(char) }
            }
        }
    }

    return stacks.map { it.last() }.joinToString("")
}

fun main() {
    val part1Answer: String
    val part1Time = measureTimeMillis { part1Answer = part1() }
    println("Part one:")
    println("Answer: $part1Answer")
    println("Time: $part1Time")

    val part2Answer: String
    val part2Time = measureTimeMillis { part2Answer = part2() }
    println("Part two:")
    println("Answer: $part2Answer")
    println("Time: $part2Time")
}
