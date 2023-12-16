package day06

import println
import readInputPath

fun main() {
    val testInput = readInputPath("src/day06/day06_test.txt")
    val input = readInputPath("src/day06/day06.txt")
    part1(input).println()
    part2(input).println()
}

fun part1(input: List<String>): Int {
    val numbers = input
        .map {
            it.split("\\D+".toRegex())
                .drop(1)
                .map { it.toInt() }
        }
        .let { lists ->
            List(lists.first().size) { index ->
                Race(
                    t = lists[0][index],
                    x = lists[1][index]
                )
            }
        }
        .fold(1) { acc, race ->
            val possible = (1..race.t).count { (race.t - it) * it > race.x }
            acc * possible
        }


    println(numbers)
    return numbers

}

fun part2(input: List<String>): Int {
    val numbers = input
        .map {
            it.split("\\D+".toRegex())
                .drop(1)
                .joinToString("")
                .toLong()
        }
        .let { list ->
            (1..list[0]).count { (list[0] - it) * it > list[1] }
        }




    println(numbers)
    return numbers
}

data class Race(
    val t: Int,
    val x: Int
)