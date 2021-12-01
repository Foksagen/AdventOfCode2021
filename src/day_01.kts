import java.io.File

fun part1(lines: List<Int>): Int {
    var result = 0
    var current = lines.first();

    for (i in 1 until lines.size) {
        val depth = lines[i]

        if (depth > current) {
            result++
        }

        current = depth
    }

    return result
}

fun part2(lines: List<Int>): Int {
    var current = 0
    var result = 0

    for (i in 2 until lines.size) {
        val depth = lines[i] + lines[i - 1] + lines[i - 2]

        if (i != 2 && depth > current) {
            result++
        }

        current = depth
    }

    return result
}

var lines = File("input/day_01.txt").readLines().map { it.toInt() }
println(part1(lines))
println(part2(lines))