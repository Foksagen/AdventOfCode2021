import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs

fun parseLine(grid: Array<IntArray>, line: String, parseDiagonal: Boolean = false) {
    val bounds = line.split(" -> ")
    val (x1, y1) = bounds[0].split(",").map(String::toInt)
    val (x2, y2) = bounds[1].split(",").map(String::toInt)

    if (parseDiagonal && min(x1, x2) < max(x1, x2) && min(y1, y2) < max(y1, y2)) {
        val xStep = if (x1 < x2) 1 else -1
        val yStep = if (y1 < y2) 1 else -1

        (0..(abs(x1 - x2))).forEach {
            grid[y1 + yStep * it][x1 + xStep * it] += 1
        }

        return
    }

    if (min(x1, x2) < max(x1, x2) && min(y1, y2) == max(y1, y2)) {
        (min(x1, x2)..max(x1, x2)).forEach { grid[y1][it] += 1 }
        return
    }

    if (min(x1, x2) == max(x1, x2) && min(y1, y2) < max(y1, y2)) {
        (min(y1, y2)..max(y1, y2)).forEach { grid[it][x1] += 1 }
        return
    }

    if (x1 == x2 && y1 == y2) {
        grid[y1][x1] += 1
        return
    }
}

fun buildGridFrom(lines: List<String>, parseDiagonals: Boolean = false): Array<IntArray> {
    val grid = Array(1000) { IntArray(1000) { 0 } }
    lines.forEach { parseLine(grid, it, parseDiagonals) }
    return grid
}

fun part1(lines: List<String>): Int {
    return buildGridFrom(lines).sumOf { row -> row.count { it >= 2 } }
}

fun part2(lines: List<String>): Int {
    return buildGridFrom(lines, true).sumOf { row -> row.count { it >= 2 } }
}

var lines = File("input/day_05.txt").readLines()
println(part1(lines))
println(part2(lines))