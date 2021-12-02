import java.io.File

fun part1(lines: List<String>): Int {
    var horizontal = 0
    var depth = 0

    lines.forEach {
        val value = it.split(" ")[1].toInt()

        if (it.startsWith("forward")) {
            horizontal += value
        }

        if (it.startsWith("up")) {
            depth -= value
        }

        if (it.startsWith("down")) {
            depth += value
        }
    }

    return horizontal * depth
}

fun part2(lines: List<String>): Int {
    var horizontal = 0
    var depth = 0
    var aim = 0

    lines.forEach {
        val value = it.split(" ")[1].toInt()

        if (it.startsWith("forward")) {
            horizontal += value
            depth += aim * value
        }

        if (it.startsWith("up")) {
            aim -= value
        }

        if (it.startsWith("down")) {
            aim += value
        }
    }

    return horizontal * depth
}

val lines = File("input/day_02.txt").readLines()
println(part1(lines))
println(part2(lines))