import java.io.File
import kotlin.system.measureTimeMillis

class Tree(var height: Int, var isSeen: Boolean = false, var scenicScore: Int = 0)


private fun part1(): Int {
    val board = File("src/main/resources/day08.txt").bufferedReader().readLines()
        .map { row -> row.toCharArray().map { tree -> Tree(tree.toString().toInt()) } }

    // Row left to right
    for (rowIndex in 0..board.lastIndex) {
        var highest = -1

        for (treeIndex in 0..board[rowIndex].lastIndex) {
            val tree = board[rowIndex][treeIndex]

            if (tree.height > highest) {
                tree.isSeen = true
                highest = tree.height
            }
        }
    }

    // Row right to left
    for (rowIndex in 0..board.lastIndex) {
        var highest = -1

        for (treeIndex in board[rowIndex].lastIndex downTo 0) {
            val tree = board[rowIndex][treeIndex]

            if (tree.height > highest) {
                tree.isSeen = true
                highest = tree.height
            }
        }
    }

    // Column left to right
    for (treeIndex in 0..board[0].lastIndex) {
        var highest = -1

        for (rowIndex in 0..board.lastIndex) {
            val tree = board[rowIndex][treeIndex]

            if (tree.height > highest) {
                tree.isSeen = true
                highest = tree.height
            }
        }
    }

    // Column right to left
    for (treeIndex in 0..board[0].lastIndex) {
        var highest = -1

        for (rowIndex in board.lastIndex downTo 0) {
            val tree = board[rowIndex][treeIndex]

            if (tree.height > highest) {
                tree.isSeen = true
                highest = tree.height
            }
        }
    }

    return board.flatten().filter { it.isSeen }.size
}

private fun part2(): Int {
    val board = File("src/main/resources/day08.txt").bufferedReader().readLines()
        .map { row -> row.toCharArray().map { tree -> Tree(tree.toString().toInt()) } }

    fun getView(trees: List<Tree>, tree: Tree): Int {
        return minOf(trees.size, trees.takeWhile { it.height < tree.height }.size + 1)
    }

    board.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, tree ->
            run {
                if (rowIndex == 0 || columnIndex == 0 || rowIndex == board.lastIndex || columnIndex == board[0].lastIndex) {
                    // Do nothing, edges always have a scenic score of 0
                } else {
                    val leftTrees = board[rowIndex].subList(0, columnIndex).reversed()
                    val rightTrees = board[rowIndex].subList(columnIndex + 1, board[rowIndex].size)
                    val topTrees = board.subList(0, rowIndex).map { it[columnIndex] }.reversed()
                    val bottomTrees = board.subList(rowIndex + 1, board.size).map { it[columnIndex] }

                    val left = getView(leftTrees, tree)
                    val right = getView(rightTrees, tree)
                    val top = getView(topTrees, tree)
                    val bottom = getView(bottomTrees, tree)

                    tree.scenicScore = left * right * top * bottom
                }
            }
        }
    }

    return board.flatten().maxOf { it.scenicScore }
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
