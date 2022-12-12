import java.io.File
import kotlin.system.measureTimeMillis

private fun getDirectorySizes(): HashMap<String, Int> {
    val currentDirectory = ArrayDeque<String>()
    val directorySizes = HashMap<String, Int>()

    File("src/main/resources/day07.txt").bufferedReader().forEachLine { line ->
        run {
            if (line.startsWith("\$ cd ")) {
                val command = line.substring(5, line.length)

                if (command == "/") {
                    currentDirectory.removeAll(currentDirectory)
                } else if (command == "..") {
                    currentDirectory.removeLast()
                } else {
                    currentDirectory.add(command)

                    val path = currentDirectory.joinToString("/")


                    if (!directorySizes.contains(path)) {
                        directorySizes[path] = 0
                    }
                }
            } else if (line.startsWith("dir ")) {
                // Ignore
            } else if (line.startsWith("$ ls")) {
                // Ignore
            } else {
                val size = line.split(" ")[0].toInt()

                for (i in 0..currentDirectory.size) {
                    val path = currentDirectory.subList(0, i).joinToString("/")

                    directorySizes[path] = (directorySizes[path] ?: 0) + size
                }
            }
        }
    }

    return directorySizes
}

private const val totalSpace = 70_000_000
private const val neededSpace = 30_000_000


private fun part1(): Int {
    return getDirectorySizes().filter { it.value <= 100_000 }.values.sum()
}


private fun part2(): Int {
    val directorySizes = getDirectorySizes()

    val usedSpace = directorySizes[""] ?: 0
    val freeSpace = totalSpace - usedSpace

    return directorySizes.filter { it.value >= neededSpace - freeSpace }.values.min()
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
