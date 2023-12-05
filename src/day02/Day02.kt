package day02

import println
import readInputPath

fun main() {
    val testInput = readInputPath("src/day02/day02_test.txt")
    val input = readInputPath("src/day02/day02.txt")
    part1(input).println()
    part2(input).println()
}

val redCubes = Cube.Red(12)
val greenCubes = Cube.Green(13)
val blueCubes = Cube.Blue(14)

fun part1(input: List<String>): Int {
    return input.sumOf { line ->
        val id = line.substring(startIndex = 5, endIndex = line.indexOf(":")).toInt()
        val sets = gameSetsFromLine(line)

        val isSetPossible = sets.map { subset ->
            val isSubsetPossible = subset.map { cube ->
                when (cube) {
                    is Cube.Blue -> cube.count <= blueCubes.count
                    is Cube.Green -> cube.count <= greenCubes.count
                    is Cube.Red -> cube.count <= redCubes.count
                }
            }.all { isPossible -> isPossible }
            isSubsetPossible
        }.all { isPossible -> isPossible }

        if (isSetPossible) id else 0
    }
}

// fewest number of cubes of each color in each game
// hat could have been in the bag
fun part2(input: List<String>): Int {
    return input.sumOf { line ->
        var maxRed: Int = 1
        var maxBlue: Int = 1
        var maxGreen: Int = 1
        val sets = gameSetsFromLine(line)
        sets.forEach { subset ->
            subset.forEach { cube ->
                when (cube) {
                    is Cube.Blue -> cube.count.takeIf { it >= maxBlue }?.let { maxBlue = cube.count }
                    is Cube.Green -> cube.count.takeIf { it >= maxGreen }?.let { maxGreen = cube.count }
                    is Cube.Red -> cube.count.takeIf { it >= maxRed }?.let { maxRed = cube.count }
                }
            }
        }

        maxBlue * maxGreen * maxRed
    }
}

private fun gameSetsFromLine(line: String): List<List<Cube>> {
    return line.substring(startIndex = line.indexOf(":") + 2)
            .split("; ")
            .map { subset ->
                subset.split(", ")
                        .map { cubeDraw ->
                            when {
                                cubeDraw.contains("red") -> Cube.Red(cubeDraw.substringBefore(" ").toInt())
                                cubeDraw.contains("blue") -> Cube.Blue(cubeDraw.substringBefore(" ").toInt())
                                cubeDraw.contains("green") -> Cube.Green(cubeDraw.substringBefore(" ").toInt())
                                else -> error("Unreachable")
                            }
                        }

            }
}

sealed interface Cube {
    data class Red(val count: Int) : Cube
    data class Green(val count: Int) : Cube
    data class Blue(val count: Int) : Cube
}