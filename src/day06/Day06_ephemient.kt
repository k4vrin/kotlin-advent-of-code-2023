package day06

import println
import readInputPath
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt


fun main() {
    val testInput = readInputPath("src/day06/day06_test.txt")
    val input = readInputPath("src/day06/day06.txt")

    Day06_ephemient.part1(input).println()
    Day06_ephemient.part2(input).println()

}

object Day06_ephemient {

    private val NUMBER = """\d+""".toRegex()

    private fun winCount(time: Long, distance: Long): Long {
        // x * (time - x) > distance
        // x^2 - time*x < -distance
        // x^2 - time*x + (time/2)^2 < (time/2)^2 - distance
        // sqrt((x - time/2)^2) < sqrt((time/2)^2 - distance)
        // abs(x - time/2) < sqrt((time/2)^2 - distance)
        val b = time / 2.0
        val d = sqrt(b * b - distance)
        // Calculate the distance between first root and second root
        return (ceil(b + d - 1) - floor(b - d + 1) + 1).toLong()
    }

    fun part1(input: List<String>): Long =
        NUMBER.findAll(input.first()).map { it.value.toLong() }
            .zip(NUMBER.findAll(input[1]).map { it.value.toLong() }, ::winCount)
            .reduce(Long::times)

    fun part2(input: List<String>): Long = winCount(
        time = input.first().filter(Char::isDigit).toLong(),
        distance = input[1].filter(Char::isDigit).toLong()
    )
}
