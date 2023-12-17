package day05

import println
import readInputPath
import kotlin.system.measureTimeMillis

fun main() {
    val testInput = readInputPath("src/day05/day05_test.txt")
    val input = readInputPath("src/day05/day05.txt")

    part1(input).println()
    measureTimeMillis {
        part2(input).println()
    }.also { println("Time millis: $it") }
}


// a list of winning numbers (|) a list of numbers you have.
// figure out which of the numbers you have appear in the list of winning numbers.
// The first match makes the card worth one point and each match after the first doubles the point value of that card.
// sum of each card point
fun part1(input: List<String>): Long {
    val seeds = input.first().split("\\D+".toRegex()).drop(1).map { it.toLong() }
    val seedMaps = seedMaps(input)

    val locations = seedMaps.fold(seeds) { acc, seedMap ->
        acc.map { seed ->
            val addition = seedMap.sources
                .firstOrNull { it.lower <= seed && it.upper >= seed }?.addition ?: 0
            seed + addition
        }
    }

    return locations.min()
}

// 1 millisecond
fun part2(input: List<String>): Long {
    val seeds = input.first().split("\\D+".toRegex())
        .drop(1)
        .chunked(2)
        .map {
            MapRange(
                lower = it.first().toLong(),
                upper = it.first().toLong() + it.last().toLong(),
                addition = 0
            )
        }

    val seedMaps = seedMaps(input)
    println(seeds)
    println("***************")

    val locations = seedMaps.fold(seeds) { acc, seedMap ->
        val accp = acc.flatMap { mapRange ->

            println(seedMap)
            println(mapRange)

            val list = mutableListOf<MapRange>()

            val wholeAdd =
                seedMap.sources.firstOrNull { it.lower <= mapRange.lower && it.upper >= mapRange.upper }?.addition


            val isNotAdd = seedMap.sources.all { it.lower > mapRange.upper || it.upper < mapRange.lower }

            if (wholeAdd == null && !isNotAdd) {
                val lowerPartial =
                    seedMap.sources.firstOrNull { it.lower <= mapRange.lower && it.upper > mapRange.lower }
                lowerPartial?.let {
                    println("lowerPartial: $it")
                    val upperPartial =
                        seedMap.sources.firstOrNull { it.upper >= mapRange.upper && it.lower < mapRange.upper }
                    list.add(
                        MapRange(
                            lower = mapRange.lower,
                            upper = if (upperPartial != null) lowerPartial.upper - 1 else lowerPartial.upper,
                            addition = 0
                        )
                    )
                    upperPartial?.let {
                        println("upperPartial: $it")
                        list.add(
                            MapRange(
                                lower = upperPartial.lower.coerceAtLeast(lowerPartial.upper),
                                upper = mapRange.upper,
                                addition = 0
                            )
                        )

//                        if (upperPartial.lower != lowerPartial.upper) {
//                            list.add(MapRange(lower = lowerPartial.upper + 1, upper = upperPartial.lower - 1, addition = 0))
//                        }

                    }

                    if (upperPartial == null) {
                        list.add(MapRange(lower = lowerPartial.upper + 1, upper = mapRange.upper, addition = 0))
                    }
                }

                if (lowerPartial == null) {
                    val upperPartial2 =
                        seedMap.sources.firstOrNull { it.upper >= mapRange.upper && it.lower < mapRange.upper }
                    upperPartial2?.let {
                        println("upperPartial2: $it")
                        val lowerPartial2 =
                            seedMap.sources.firstOrNull { it.lower <= mapRange.lower && it.upper > mapRange.lower }
                        list.add(
                            MapRange(
                                lower = if (lowerPartial2 != null) upperPartial2.lower + 1 else upperPartial2.lower,
                                upper = mapRange.upper,
                                addition = 0
                            )
                        )
                        lowerPartial2?.let {
                            println("lowerPartial2: $it")
                            list.add(
                                MapRange(
                                    lower = mapRange.lower,
                                    upper = lowerPartial2.upper.coerceAtMost(upperPartial2.lower),
                                    addition = 0
                                )
                            )
//                            if (lowerPartial2.upper != upperPartial2.lower) {
//                                list.add(MapRange(lower = lowerPartial2.upper + 1, upper = upperPartial2.lower - 1, addition = 0))
//                            }
                        }

                        if (lowerPartial2 == null) {
                            list.add(MapRange(lower = mapRange.lower, upper = upperPartial2.lower - 1, addition = 0))
                        }
                    }
                }
            } else {
                list.add(mapRange)
            }

            println(wholeAdd)
            println(list)
            println("*/*/*/*/*")
            list
        }


        accp.map { seed ->
            println("Seed before add: $seed")
            val addition = seedMap.sources
                .firstOrNull { it.lower <= seed.lower && it.upper >= seed.upper }?.addition ?: 0
            println("Add: $addition")
            MapRange(lower = seed.lower + addition, upper = seed.upper + addition, addition = seed.addition).also {
                println("Seed after add: $it")
            }
        }.also { println(it);println("___________") }


    }

    println(locations.sortedBy { it.lower })

    return locations.minBy { it.lower }.lower
}

fun part2Cleaner(input: List<String>): Long {
    val seeds = input.first().split("\\D+".toRegex())
        .drop(1)
        .chunked(2)
        .map {
            MapRange(
                lower = it.first().toLong(),
                upper = it.first().toLong() + it.last().toLong(),
                addition = 0
            )
        }

    val seedMaps = seedMaps(input)

    val locations = seedMaps.fold(seeds) { acc, seedMap ->
        acc.flatMap { mapRange ->

            val isComplete = seedMap.sources
                .any { it.lower <= mapRange.lower && it.upper >= mapRange.upper }
            val isNotAddable = seedMap.sources
                .all { it.lower > mapRange.upper || it.upper < mapRange.lower }
            val isPartiallyAddable = !isComplete && !isNotAddable

            buildList {
                when {
                    isPartiallyAddable -> {
                        seedMap.sources
                            .firstOrNull {
                                it.lower <= mapRange.lower && it.upper > mapRange.lower
                            }
                            ?.let { lowerPart ->
                                val upperPart =
                                    seedMap.sources
                                        .firstOrNull { it.upper >= mapRange.upper && it.lower < mapRange.upper }
                                add(
                                    MapRange(
                                        lower = mapRange.lower,
                                        upper = lowerPart.upper - (upperPart?.let { 1 } ?: 0),
                                        addition = 0
                                    )
                                )

                                upperPart?.let {
                                    add(
                                        MapRange(
                                            lower = upperPart.lower.coerceAtLeast(lowerPart.upper),
                                            upper = mapRange.upper,
                                            addition = 0
                                        )
                                    )
                                } ?: run {
                                    add(
                                        MapRange(
                                            lower = lowerPart.upper + 1,
                                            upper = mapRange.upper,
                                            addition = 0
                                        )
                                    )
                                }
                            } ?: run {
                            seedMap.sources
                                .firstOrNull { it.upper >= mapRange.upper && it.lower < mapRange.upper }
                                ?.let { upperPart ->
                                    val lowerPart =
                                        seedMap.sources.firstOrNull { it.lower <= mapRange.lower && it.upper > mapRange.lower }
                                    add(
                                        MapRange(
                                            lower = upperPart.lower + (lowerPart?.let { 1 } ?: 0),
                                            upper = mapRange.upper,
                                            addition = 0
                                        )
                                    )

                                    lowerPart?.let {
                                        add(
                                            MapRange(
                                                lower = mapRange.lower,
                                                upper = lowerPart.upper.coerceAtMost(upperPart.lower),
                                                addition = 0
                                            )
                                        )
                                    } ?: run {
                                        add(
                                            MapRange(
                                                lower = mapRange.lower,
                                                upper = upperPart.lower - 1,
                                                addition = 0
                                            )
                                        )
                                    }
                                }
                        }

                    }
                    // isComplete || isNotAddable
                    else -> add(mapRange)
                }
            }
        }
            .map { seed ->
                seedMap.sources
                    .firstOrNull { it.lower <= seed.lower && it.upper >= seed.upper }
                    ?.addition
                    .orZero()
                    .let { addition ->
                        MapRange(
                            lower = seed.lower + addition,
                            upper = seed.upper + addition,
                            addition = seed.addition
                        )
                    }
            }
    }

    return locations.minBy { it.lower }.lower
}



data class SeedMap(
    val name: String,
    val destinations: List<MapRange>,
    val sources: List<MapRange>
)

data class MapRange(
    val lower: Long,
    val upper: Long,
    val addition: Long
)

private fun seedMaps(input: List<String>) =
    input.drop(1).fold(mutableListOf<MutableList<String>>()) { acc, s ->
        if (s.isBlank()) {
            acc.add(mutableListOf())

        } else {
            acc.last().add(s)
        }
        acc
    }.map {
        val name = it.first().dropLast(4)
        val pairs = it.drop(1).map {
            val numbers = it.split("\\D+".toRegex())

            MapRange(
                lower = numbers.first().toLong(),
                upper = numbers.first().toLong() + numbers.last().toLong(),
                addition = numbers.first().toLong() - numbers[1].toLong()
            ) to MapRange(
                lower = numbers[1].toLong(),
                upper = numbers[1].toLong() + numbers.last().toLong(),
                addition = numbers.first().toLong() - numbers[1].toLong()
            )
        }
        SeedMap(
            name = name,
            destinations = pairs.map { pair -> pair.first },
            sources = pairs.map { pair -> pair.second }
        )
    }

fun Long?.orZero(): Long = this ?: 0