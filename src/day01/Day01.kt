package day01

import println
import readInputPath

fun main() {
//    val testInput = readInputPath("src/day01/day01_test.txt")
    val input = readInputPath("src/day01/day01.txt")
    part1(input).println()
    part2(input).println()
}

fun part1(input: List<String>): Int {
    return input.sumOf { line ->
        val a = line.first { ch -> ch.isDigit() }
        val b = line.last { ch -> ch.isDigit() }
        "$a$b".toInt()
    }

}

    val letterNumMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val letterNumIndexes = mutableMapOf<Int, String>()
            val digitIndexes = mutableMapOf<Int, String>()
            letterNumMap.keys.forEach {
                if (line.contains(it)) {
                    letterNumIndexes[line.indexOf(it)] = letterNumMap[it].toString()
                    letterNumIndexes[line.lastIndexOf(it)] = letterNumMap[it].toString()
                }
            }
            line.forEach {
                if (it.isDigit()) {
                    digitIndexes[line.indexOf(it)] = it.toString()
                    digitIndexes[line.lastIndexOf(it)] = it.toString()
                }
            }
            val map = (letterNumIndexes + digitIndexes).toSortedMap()
            val a = map.values.first()
            val b = map.values.last()
            (a + b).toInt()
        }
    }