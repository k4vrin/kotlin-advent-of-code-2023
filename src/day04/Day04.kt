package day04

import println
import readInputPath

fun main() {
    val testInput = readInputPath("src/day04/day04_test.txt")
    val input = readInputPath("src/day04/day04.txt")
    
    part1(input).println()
    part2(input).println()
}


    // a list of winning numbers (|) a list of numbers you have.
    // figure out which of the numbers you have appear in the list of winning numbers.
    // The first match makes the card worth one point and each match after the first doubles the point value of that card.
    // sum of each card point
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val winningNumbers = line.winningNumbers()
            val ourNumbers = line.ourNumbers()

            winningNumbers.fold(initial = 0) { acc, i ->
                if (ourNumbers.contains(i)) {
                    if (acc == 0) acc + 1 else acc + acc
                } else acc
            }.toLong()
        }.toInt()
    }


    // 1 millisecond
    fun part2(input: List<String>): Int {
        return input
            .map { line -> 
                val winningNumbers = line.winningNumbers()
                val ourNumbers = line.ourNumbers()
                winningNumbers.count { it in ourNumbers }
            }
            .reversed()
            .fold(emptyList<Int>()) { acc, i ->  
                val sum = 1 + (0..<i).sumOf { acc[it] }
                listOf(sum) + acc
            }
            .sum()
    }

// 4066 milliseconds
private fun part2First(input: List<String>): Int {
    val originalCards = input.map { line ->
        val number = line.substring(startIndex = 5, endIndex = line.indexOf(":")).trim().toInt()
        val winningNumbers = line.winningNumbers()
        val ourNumbers = line.ourNumbers()

        Card(number, winningNumbers, ourNumbers)
    }

    val cards = originalCards.toMutableList()

    var i = 0
    while (i < cards.size) {
        val card = cards[i]
        val count = card.winningNumbers.filter { card.ourNumbers.contains(it) }.size

        repeat(count) {
            cards.add(cards[card.number + it])
        }
        i++
    }

    return cards.size
}

private fun String.winningNumbers(): List<Int> = this.substring(startIndex = this.indexOf(":") + 1, endIndex = this.indexOf("|"))
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.toInt() }

private fun String.ourNumbers(): List<Int> = this.substring(startIndex = this.indexOf("|") + 1)
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.toInt() }


data class Card(
        val number: Int,
        val winningNumbers: List<Int>,
        val ourNumbers: List<Int>
)