import java.io.File
import kotlin.math.pow

fun part1(lines: List<String>): Int {
    val zeroes = Array(lines[0].length) { 0 }
    val ones = Array(zeroes.size) { 0 }

    lines.forEach { line ->

        line.forEachIndexed { index, char ->

            if (char.code == 49) {
                ones[index] += 1

            } else {
                zeroes[index] += 1
            }
        }
    }

    val gamma = zeroes.indices.sumOf {
        val bit = if (ones[it] > zeroes[it]) 1 else 0
        (bit * 2.0.pow(zeroes.size - it - 1)).toInt()
    }

    val epsilon = zeroes.indices.sumOf {
        val bit = if (ones[it] < zeroes[it]) 1 else 0
        (bit * 2.0.pow(zeroes.size - it - 1)).toInt()
    }

    return gamma * epsilon
}

fun part2(lines: List<String>): Int {

    fun String.valueAtBit(bit: Int) = this[bit].code - 48

    fun filterByBit(lines: List<String>, bit: Int, criteria: (Int, Int) -> Boolean): List<String> {
        var zeroes = 0
        var ones = 0

        lines.forEach {

            if (it[bit].code == 49) {
                ones += 1

            } else {
                zeroes += 1
            }
        }

        val bitValue = if (criteria(zeroes, ones)) 1 else 0

        return lines.filter { it.valueAtBit(bit) == bitValue }
    }


    var oxygenValues = filterByBit(lines, 0) { zeroes, ones -> zeroes <= ones }
    var bit = 1

    while (oxygenValues.size > 1) {
        oxygenValues = filterByBit(oxygenValues, bit++) { zeroes, ones -> zeroes <= ones }
    }

    var scrubberValues = filterByBit(lines, 0) { zeroes, ones -> zeroes > ones }
    bit = 1

    while (scrubberValues.size > 1) {
        scrubberValues = filterByBit(scrubberValues, bit++) { zeroes, ones -> zeroes > ones }
    }

    val oxygenRating = oxygenValues.first().let { value ->
        value.indices.sumOf {
            (value.valueAtBit(it) * 2.0.pow(value.length - it - 1)).toInt()
        }
    }

    val scrubberRating = scrubberValues.first().let { value ->
        value.indices.sumOf {
            (value.valueAtBit(it) * 2.0.pow(value.length - it - 1)).toInt()
        }
    }

    return oxygenRating * scrubberRating
}

var lines = File("input/day_03.txt").readLines()
println(part1(lines))
println(part2(lines))