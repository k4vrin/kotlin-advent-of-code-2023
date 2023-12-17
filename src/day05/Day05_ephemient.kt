package day05

import println
import readInputPath

fun main() {
    val testInput = readInputPath("src/day05/day05_test.txt")
    val input = readInputPath("src/day05/day05.txt")
    val seeds = seeds(input)
    val mappings = mappings(input)


    part1(mappings, seeds).println()
    part2(mappings, seeds).println()
}

fun part1(mappings: List<List<Mapping>>, seeds: List<Long>): Long = mappings.solve(seeds.map { it..it })

fun part2(mappings: List<List<Mapping>>, seeds: List<Long>): Long = mappings.solve(seeds.chunked(2) { (start, length) -> start until start + length })

private fun List<List<Mapping>>.solve(seedRanges: List<LongRange>): Long = seedRanges.flatMap {
    this.fold(listOf(it)) { acc, mappings ->
        buildList {
            for (range in acc) {
                mappings.filter { mapping -> range in mapping }.fold(range.first) { first, mapping ->
                    if (first < mapping.source) add(first until mapping.source)
                    val start = maxOf(first, mapping.source)
                    val end = minOf(range.last + 1, mapping.source + mapping.length)
                    val offset = mapping.dest - mapping.source
                    add(start + offset until end + offset)
                    end
                }.takeIf { last -> last <= range.last }?.let { last -> add(last..range.last) }
            }
        }
    }
}.minOf { it.first }

private fun mappings(input: List<String>): List<List<Mapping>> = input.drop(1)
    .fold(mutableListOf<MutableList<String>>()) { acc, s ->
        if (s.isBlank()) {
            acc.add(mutableListOf())
        } else {
            acc.last().add(s)
        }
        acc
    }
    .map { wholeMap ->
        wholeMap.drop(1)
            .mapNotNull { eachMap ->
                val (dest, source, size) = eachMap.split("\\D+".toRegex())
                Mapping(
                    dest = dest.toLongOrNull() ?: return@mapNotNull null,
                    source = source.toLongOrNull() ?: return@mapNotNull null,
                    length = size.toLongOrNull() ?: return@mapNotNull null
                )
            }
            .sortedBy { it.source }
    }


private fun seeds(input: List<String>): List<Long> = input.first()
    .split(' ')
    .mapNotNull { it.toLongOrNull() }

data class Mapping(val dest: Long, val source: Long, val length: Long) {
    operator fun contains(range: LongRange): Boolean =
        !range.isEmpty() && source <= range.last && range.first < source + length
}