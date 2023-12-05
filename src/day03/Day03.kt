package day03

import println
import readInputPath


fun main() {
    val testInput = readInputPath("src/day03/day03_test.txt")
    val testInput2 = readInputPath("src/day03/day03_test_2.txt")
    val input = readInputPath("src/day03/day03.txt")

    part1(input).println()
    part2(input).println()
}


    // add up all the part numbers in the engine schematic
    // any number adjacent to a symbol, even diagonally, is a "part number"
    fun part1(input: List<String>): Int {
        val addedParts = mutableListOf<PartNumber>()
        input.forEachIndexed { lineIndex, line ->
            val symbolIndexMap = mutableMapOf<Int, Char>()

            line.forEachIndexed { charIndex, ch ->
                if (ch.isSymbol()) symbolIndexMap[charIndex] = ch
            }
            symbolIndexMap.keys.forEach { symIndex ->
                val symIndexRange = IntRange(start = if (symIndex > 0) symIndex - 1 else symIndex, endInclusive = if (symIndex == line.length - 1) symIndex else symIndex + 1)
                listOf(lineIndex - 1, lineIndex, lineIndex + 1).forEach { row ->
                    input.forEachPartNumberInLine(
                        lineIndex = row,
                        symIndexRange = symIndexRange
                    ) { partNumber -> if (!addedParts.contains(partNumber)) addedParts.add(partNumber) }
                }
            }
        }
        return addedParts.sumOf { it.value }
    }

    // gear is any * symbol that is adjacent to exactly two part numbers.
    // gear ratio is the result of multiplying those two numbers together.
    // find the gear ratio of every gear and add them all
    fun part2(input: List<String>): Int {

        val addedParts = mutableListOf<Pair<PartNumber, PartNumber>>()

        input.forEachIndexed { lineIndex, line ->
            val gearIndexMap = mutableMapOf<Int, Char>()

            line.forEachIndexed { charIndex, ch ->
                if (ch == '*') gearIndexMap[charIndex] = ch
            }

            for ((symIndex, gear) in gearIndexMap.entries) {
                val symIndexRange = IntRange(start = if (symIndex > 0) symIndex - 1 else symIndex, endInclusive = if (symIndex == line.length - 1) symIndex else symIndex + 1)
                val gearNumbers = mutableListOf<PartNumber>()
                listOf(lineIndex - 1, lineIndex, lineIndex + 1).forEach { row ->
                    input.forEachPartNumberInLine(
                        lineIndex = row,
                        symIndexRange = symIndexRange
                    ) { partNumber -> if (!gearNumbers.contains(partNumber)) gearNumbers.add(partNumber) }
                }
                if (gearNumbers.size != 2) continue
                addedParts.add(Pair(gearNumbers.first(), gearNumbers[1]))
            }


        }

        return addedParts.sumOf { it.first.value * it.second.value }
    }



data class PartNumber(
        val latitude: IntRange,
        val longitude: Int,
        val value: Int
)

fun Char.isSymbol(): Boolean = !this.isDigit() && !this.isLetter() && this != '.' && this != '\n'
inline fun List<String>.forEachPartNumberInLine(lineIndex: Int, symIndexRange: IntRange, ifIsPartNumber: (PartNumber) -> Unit) {
    val line = this.getOrNull(lineIndex)?.replace("\\D".toRegex(), "a")
    val l = buildString {
        if (!line.isNullOrBlank()) {
            append("a")
            append(line)
            append("a")
        }
    }.takeIf { it.isNotBlank() }
    val numbers = line?.split("a")?.filter { it.isNotEmpty() }?.map { it.toInt() }
    val numberRanges = mutableListOf<Pair<IntRange, Int>>()
    numbers?.forEach { number ->

        val startIndex = l!!.indexOf("a${number}a")
        val numberRange = IntRange(start = startIndex, endInclusive = startIndex + number.toString().length - 1)

        numberRanges.lastOrNull { it.first == numberRange }?.let { range ->
            val sI = l.indexOf("a${number}a", startIndex = range.first.last + 1)
            val nR = IntRange(start = sI, endInclusive = sI + number.toString().length - 1)
            numberRanges.add(Pair(nR, number))
        } ?: numberRanges.add(Pair(numberRange, number))

    }

    numberRanges.forEach { numberRangeAndNumber ->
        symIndexRange.map { numberRangeAndNumber.first.contains(it) }
                .any { isPartNumber -> isPartNumber }
                .takeIf { it }
                ?.let {
                    ifIsPartNumber(
                            PartNumber(
                                    latitude = numberRangeAndNumber.first,
                                    longitude = lineIndex + 1,
                                    value = numberRangeAndNumber.second
                            )
                    )
                }
    }
}