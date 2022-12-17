import java.io.File
import kotlin.system.measureTimeMillis

open class ClockCircuit(startRegister: Int) {
    open var register = startRegister

    open fun incrementCycle() {}

    fun performInstruction(line: String) {
        val instructions = line.split(" ")

        when (instructions[0]) {
            "noop" -> incrementCycle()
            "addx" -> {
                incrementCycle()
                incrementCycle()
                register += instructions[1].toInt()
            }
        }
    }
}

class CPU : ClockCircuit(1) {
    private var cycle = 0
    private var signalStrength = hashMapOf<Int, Int>()

    override fun incrementCycle() {
        cycle++

        if (cycle % 20 == 0) {
            signalStrength[cycle] = register * cycle
        }
    }

    fun getSignalStrengthSum(cycles: Array<Int>): Int {
        return cycles.sumOf { signalStrength[it] ?: 0 }
    }
}

class CRT : ClockCircuit(2) {
    private var cycle = 0
    private var image = arrayListOf<Boolean>()

    override fun incrementCycle() {
        cycle++

        image.add((register - 1..register + 1).contains(cycle % 40))
    }

    fun getImage(): String {
        return image.chunked(40).joinToString("\n") { it.joinToString("") { value -> if (value) "#" else " " } }
    }
}

private fun part1(): Int {
    val cpu = CPU()

    File("src/main/resources/day10.txt").bufferedReader().forEachLine { line -> cpu.performInstruction(line) }

    return cpu.getSignalStrengthSum(arrayOf(20, 60, 100, 140, 180, 220))
}

private fun part2(): String {
    val crt = CRT()

    File("src/main/resources/day10.txt").bufferedReader().forEachLine { line -> crt.performInstruction(line) }

    return crt.getImage()
}

fun main() {
    val part1Answer: Int
    val part1Time = measureTimeMillis { part1Answer = part1() }
    println("Part one:")
    println("Answer: $part1Answer")
    println("Time: $part1Time")

    val part2Answer: String
    val part2Time = measureTimeMillis { part2Answer = part2() }
    println("Part two:")
    println("Answer: \n$part2Answer")
    println("Time: $part2Time")
}
