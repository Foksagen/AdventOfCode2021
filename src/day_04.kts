import java.io.File

class Board(vararg numbers: String) {
    private val grid = arrayOfNulls<IntArray>(5)

    init {
        numbers.indices.forEach { index ->
            grid[index] = numbers[index]
                .windowedSequence(2, 3)
                .map { it.replace(" ", "").toInt() }
                .toList().toIntArray()
        }
    }

    val score get() = grid.indices.map { grid[it] }.sumOf { row -> row!!.filter { it >= 0 }.sum() }

    fun mark(number: Int): Boolean {
        var row = -1
        var column = -1

        grid.indices.forEach start@{ rIndex ->

            grid[rIndex]!!.indices.forEach { cIndex ->

                if (grid[rIndex]!![cIndex] == number) {
                    grid[rIndex]!![cIndex] = -1
                    row = rIndex
                    column = cIndex
                    return@start
                }
            }
        }

        if (row < 0 && column < 0) {
            return false
        }

        return grid.indices.map { grid[row]!![it] }.all { it < 0 } || grid.indices.map { grid[it]!![column] }.all { it < 0 }
    }
}

inline fun executeOnData(lines: List<String>, crossinline function: (List<Int>, MutableList<Board>) -> Int): Int {
    val numbers = lines[0].split(",").map(String::toInt)
    val boards = mutableListOf<Board>()

    IntProgression.fromClosedRange(6, lines.size, 6).forEach {
        boards += Board(lines[it - 4], lines[it - 3], lines[it - 2], lines[it - 1], lines[it])
    }

    return function(numbers, boards)
}

fun part1(numbers: List<Int>, boards: List<Board>): Int {

    numbers.forEach { number ->

        boards.forEach {

            if (it.mark(number)) {
                return number * it.score
            }
        }
    }

    return -1
}

fun part2(numbers: List<Int>, boards: MutableList<Board>): Int {
    val winners = mutableSetOf<Board>()
    var lastNumber = -1

    numbers.forEach { number ->
        val iterator = boards.iterator()

        while (iterator.hasNext()) {
            val board = iterator.next()

            if (board.mark(number)) {
                winners.add(board)
                lastNumber = number
                iterator.remove()
            }
        }
    }

    return winners.last().score * lastNumber
}

var lines = File("input/day_04.txt").readLines()
println(executeOnData(lines, ::part1))
println(executeOnData(lines, ::part2))