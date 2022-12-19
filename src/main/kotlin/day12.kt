import java.io.File
import kotlin.system.measureTimeMillis

private class Node(val value: Int, var neighbors: List<Node> = listOf())

private fun getNodes(filterNeighbors: (nodeValue: Int, neighborValue: Int) -> Boolean): Triple<List<List<Node>>, Node?, Node?> {
    var startNode: Node? = null
    var endNode: Node? = null

    val nodes = File("src/main/resources/day12.txt")
        .bufferedReader()
        .readLines()
        .map { line ->
            line.toCharArray().map { value ->
                run {
                    when (value) {
                        'S' -> {
                            val node = Node('a'.code)

                            startNode = node

                            node
                        }

                        'E' -> {
                            val node = Node('z'.code)

                            endNode = node

                            node
                        }

                        else -> Node(value.code)
                    }
                }
            }
        }

    nodes.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, node ->
            run {
                val neighbors = mutableListOf<Node>()

                // Top
                if (rowIndex > 0) neighbors.add(nodes[rowIndex - 1][columnIndex])
                // Bottom
                if (rowIndex < nodes.lastIndex) neighbors.add(nodes[rowIndex + 1][columnIndex])
                // Left
                if (columnIndex > 0) neighbors.add(nodes[rowIndex][columnIndex - 1])
                // Right
                if (columnIndex < row.lastIndex) neighbors.add(nodes[rowIndex][columnIndex + 1])

                node.neighbors = neighbors.filter { filterNeighbors(node.value, it.value) }
            }
        }
    }

    return Triple(nodes, startNode, endNode)
}

private fun dijkstrasShortestPath(nodes: List<Node>, start: Node): Map<Node, Int> {
    val distances = nodes.associateWith { Int.MAX_VALUE }.toMutableMap()
    val shortestPath = nodes.associateWith { false }.toMutableMap()

    distances[start] = 0

    for (i in 1..nodes.size) {
        val currentNode = distances.entries.filter { !(shortestPath[it.key]!!) }.minBy { it.value }.key

        shortestPath[currentNode] = true

        if (distances[currentNode] == Int.MAX_VALUE) continue

        for (neighbor in currentNode.neighbors.filter { shortestPath[it] == false }) {
            if ((distances[currentNode]!! + 1) < distances[neighbor]!!) {
                distances[neighbor] = distances[currentNode]!! + 1
            }
        }
    }

    return distances
}

private fun part1(): Int {
    val (nodes, startNode, endNode) = getNodes { node: Int, neighbor: Int -> neighbor <= node + 1 }

    return dijkstrasShortestPath(nodes.flatten(), startNode!!)[endNode]!!
}

private fun part2(): Int {
    val (nodes, _, endNode) = getNodes { node: Int, neighbor: Int -> neighbor >= node - 1 }

    return dijkstrasShortestPath(nodes.flatten(), endNode!!).entries
        .filter { it.key.value == 'a'.code }
        .minBy { it.value }.value
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
    println("Answer: \n$part2Answer")
    println("Time: $part2Time")
}
