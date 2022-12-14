import java.io.File
import kotlin.math.abs
import kotlin.math.sign
import kotlin.system.measureTimeMillis

enum class Direction {
    R, U, L, D
}

private fun getMoves(): List<Direction> {
    return File("src/main/resources/day09.txt").bufferedReader().readLines().flatMap {
        val instruction = it.split(" ")
        val direction = Direction.valueOf(instruction[0])
        val length = instruction[1].toInt()

        List(length) { direction }
    }
}

class Knot(count: Int) {
    var x = 0
    var y = 0
    var child: Knot? = null
    val positions = hashSetOf("(0,0)")

    init {
        if (count > 1) {
            this.child = Knot(count - 1)
        }
    }


    constructor() : this(1)

    fun getLastKnot(): Knot {
        return child?.getLastKnot() ?: this
    }

    fun move(moves: List<Direction>) {
        moves.forEach { move(it) }
    }

    fun move(direction: Direction) {
        when (direction) {
            Direction.D -> y -= 1
            Direction.U -> y += 1
            Direction.L -> x -= 1
            Direction.R -> x += 1
        }

        positions.add("($x,$y)")

        child?.follow(this)
    }

    private fun follow(parent: Knot) {
        val yDiff = abs(parent.y - this.y)
        val xDiff = abs(parent.x - this.x)

        if (yDiff == 2 || xDiff == 2) {
            if (yDiff == 0) {
                this.x += sign((parent.x - this.x).toDouble()).toInt()
            } else if (xDiff == 0) {
                this.y += sign((parent.y - this.y).toDouble()).toInt()
            } else {
                this.x += sign((parent.x - this.x).toDouble()).toInt()
                this.y += sign((parent.y - this.y).toDouble()).toInt()
            }
        }

        positions.add("($x,$y)")

        child?.follow(this)

    }
}


private fun part1(): Int {
    val rope = Knot(2)

    rope.move(getMoves())

    return rope.getLastKnot().positions.size
}

private fun part2(): Int {
    val rope = Knot(10)

    rope.move(getMoves())

    return rope.getLastKnot().positions.size
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
