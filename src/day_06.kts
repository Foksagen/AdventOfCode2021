import java.io.File
import java.util.*

fun part1(data: List<Int>): Int {
    val queue = PriorityQueue(data.sorted())

    (1..80).forEach { day ->
        var count = 0

        while (queue.first() < day) {
            queue.poll()
            queue.offer(day + 6)
            count++
        }

        repeat(count) { queue.offer(day + 8) }
    }

    return queue.count()
}

fun part2(data: List<Int>): Long {
    val counts = mutableMapOf<Int, Long>().apply {
        data.forEach {
            this[it] = getOrDefault(it, 0) + 1
        }
    }

    (1..256).forEach {
        val count = counts.getOrDefault(it - 1, 0)

        if (count > 0) {
            counts[it + 6] = counts.getOrDefault(it + 6, 0) + count
            counts[it + 8] = counts.getOrDefault(it + 8, 0) + count
            counts.remove(it - 1)
        }
    }

    return counts.values.sum()
}

val data = File("input/day_06.txt").readLines().first().split(",").map(String::toInt)
println(part1(data))
println(part2(data))