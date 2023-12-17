package day07

import readInputPath

fun main() {
    val testInput2 = readInputPath("src/day07/day07_test2.txt")
    val testInput = readInputPath("src/day07/day07_test.txt")
    val input = readInputPath("src/day07/day07.txt")

    Day07.part1(input)
    Day07.part2(input)
}

object Day07 {
    fun part1(input: List<String>): Long {
        return input
            .asSequence()
            .map {
                val (cards, bid) = it.split("\\s+".toRegex())
                (cards to bid)
            }
            .fold(MutableList(7) { mutableListOf<HandType>() }) { acc, s ->
                when (val handType = HandType.createHand(s.first, s.first, s.second.toLong())) {
                    is HandType.FiveOfKind -> acc[6].add(handType)
                    is HandType.FourOfKind -> acc[5].add(handType)
                    is HandType.FullHouse -> acc[4].add(handType)
                    is HandType.ThreeOfKind -> acc[3].add(handType)
                    is HandType.TwoPair -> acc[2].add(handType)
                    is HandType.OnePair -> acc[1].add(handType)
                    is HandType.HighCard -> acc[0].add(handType)
                }
                acc
            }
            .map { list ->
                list.sortedWith { o1, o2 ->
                    val o1Weight = o1.handCards.mapNotNull { part1Score[it] }
                    val o2Weight = o2.handCards.mapNotNull { part1Score[it] }
                    when {
                        o1Weight[0] != o2Weight[0] -> o1Weight[0] - o2Weight[0]
                        o1Weight[1] != o2Weight[1] -> o1Weight[1] - o2Weight[1]
                        o1Weight[2] != o2Weight[2] -> o1Weight[2] - o2Weight[2]
                        o1Weight[3] != o2Weight[3] -> o1Weight[3] - o2Weight[3]
                        o1Weight[4] != o2Weight[4] -> o1Weight[4] - o2Weight[4]
                        o1Weight[5] != o2Weight[5] -> o1Weight[5] - o2Weight[5]
                        o1Weight[6] != o2Weight[6] -> o1Weight[6] - o2Weight[6]
                        else -> error("OutOfBound")
                    }
                }
            }
            .flatten()
            .foldIndexed(0L) { index, acc, handTypes ->
                acc + ((index + 1) * handTypes.handBid)
            }
    }

    fun part2(input: List<String>): Long {
        return input
            .asSequence()
            .map {
                val (cards, bid) = it.split("\\s+".toRegex())
                (cards to bid)
            }
            .fold(MutableList(7) { mutableListOf<HandType>() }) { acc, s ->
                val handType = if (s.first.isJoker()) {
                    HandType.createHand(s.first, s.first.transformJoker(), s.second.toLong())
                } else {
                    HandType.createHand(s.first, s.first, s.second.toLong())
                }

                when (handType) {
                    is HandType.FiveOfKind -> acc[6].add(handType)
                    is HandType.FourOfKind -> acc[5].add(handType)
                    is HandType.FullHouse -> acc[4].add(handType)
                    is HandType.ThreeOfKind -> acc[3].add(handType)
                    is HandType.TwoPair -> acc[2].add(handType)
                    is HandType.OnePair -> acc[1].add(handType)
                    is HandType.HighCard -> acc[0].add(handType)
                }
                acc
            }
            .map { list ->
                list.sortedWith { o1, o2 ->
                    val o1Weight = o1.handCards.mapNotNull { part2Score[it] }
                    val o2Weight = o2.handCards.mapNotNull { part2Score[it] }
                    when {
                        o1Weight[0] != o2Weight[0] -> o1Weight[0] - o2Weight[0]
                        o1Weight[1] != o2Weight[1] -> o1Weight[1] - o2Weight[1]
                        o1Weight[2] != o2Weight[2] -> o1Weight[2] - o2Weight[2]
                        o1Weight[3] != o2Weight[3] -> o1Weight[3] - o2Weight[3]
                        o1Weight[4] != o2Weight[4] -> o1Weight[4] - o2Weight[4]
                        o1Weight[5] != o2Weight[5] -> o1Weight[5] - o2Weight[5]
                        o1Weight[6] != o2Weight[6] -> o1Weight[6] - o2Weight[6]
                        else -> error("OutOfBound")
                    }
                }
            }
            .flatten()
            .foldIndexed(0L) { index, acc, handTypes ->
                acc + ((index + 1) * handTypes.handBid)
            }
    }

    private val part1Score = hashMapOf<Char, Int>(
        'A' to 14,
        'K' to 13,
        'Q' to 12,
        'J' to 11,
        'T' to 10,
        '9' to 9,
        '8' to 8,
        '7' to 7,
        '6' to 6,
        '5' to 5,
        '4' to 4,
        '3' to 3,
        '2' to 2,
    )
    private val part2Score = hashMapOf<Char, Int>(
        'A' to 14,
        'K' to 13,
        'Q' to 12,
        'J' to 11,
        'T' to 10,
        '9' to 9,
        '8' to 8,
        '7' to 7,
        '6' to 6,
        '5' to 5,
        '4' to 4,
        '3' to 3,
        '2' to 2,
        'J' to 1,
    )

}

sealed class HandType(val handCards: String, val handModCards: String, val handBid: Long) {
    data class FiveOfKind(val cards: String, val modCards: String, val bid: Long) : HandType(cards, modCards, bid)
    data class FourOfKind(val cards: String, val modCards: String, val bid: Long) : HandType(cards, modCards, bid)
    data class FullHouse(val cards: String, val modCards: String, val bid: Long) : HandType(cards, modCards, bid)
    data class ThreeOfKind(val cards: String, val modCards: String, val bid: Long) : HandType(cards, modCards, bid)
    data class TwoPair(val cards: String, val modCards: String, val bid: Long) : HandType(cards, modCards, bid)
    data class OnePair(val cards: String, val modCards: String, val bid: Long) : HandType(cards, modCards, bid)
    data class HighCard(val cards: String, val modCards: String, val bid: Long) : HandType(cards, modCards, bid)

    companion object {

        fun createHand(cards: String, modCards: String, bid: Long): HandType {
            return when {
                modCards.isFiveOfKind() -> FiveOfKind(cards, modCards, bid)
                modCards.isFourOfKind() -> FourOfKind(cards, modCards, bid)
                modCards.isFullHouse() -> FullHouse(cards, modCards, bid)
                modCards.isThreeOfKind() -> ThreeOfKind(cards, modCards, bid)
                modCards.isTwoPair() -> TwoPair(cards, modCards, bid)
                modCards.isOnePair() -> OnePair(cards, modCards, bid)
                else -> HighCard(cards, modCards, bid)
            }
        }
    }

}

private fun String.isJoker(): Boolean = this.contains("J", ignoreCase = true)

private fun String.transformJoker(): String =
    when {
        this.isFiveOfKind() -> this
        this.isFourOfKind() -> this.replace('J', this.first { it != 'J' })
        this.isFullHouse() -> this.replace('J', this.first { it != 'J' })
        this.isThreeOfKind() -> this.replace(
            'J',
            this.firstOrNull { it != 'J' && this.count { char -> it == char } == 3 } ?: this.first { it != 'J' }
        )

        this.isTwoPair() -> this.replace(
            'J',
            this.firstOrNull { it != 'J' && this.count { char -> it == char } == 2 } ?: this.first { it != 'J' }
        )

        this.isOnePair() -> this.replace(
            'J',
            this.firstOrNull { it != 'J' && this.count { char -> it == char } == 2 } ?: this.first { it != 'J' }
        )

        else -> this.replace('J', this.first { it != 'J' })
    }


private fun String.isFiveOfKind(): Boolean {
    return this.all { it == this[0] }
}

private fun String.isFourOfKind(): Boolean {
    return when {
        this.count { it == this[0] } >= 4 -> true
        this.count { it == this[1] } >= 4 -> true
        this.count { it == this[2] } >= 4 -> true
        this.count { it == this[3] } >= 4 -> true
        else -> false
    }
}

private fun String.isFullHouse(): Boolean {
    val chars = this.toHashSet()
    if (chars.size != 2) return false
    return when {
        this.count { it == chars.first() } == 3 && this.count { it == chars.elementAt(1) } == 2 -> true
        this.count { it == chars.elementAt(1) } == 3 && this.count { it == chars.first() } == 2 -> true
        else -> false
    }
}

private fun String.isThreeOfKind(): Boolean {
    val chars = this.toHashSet()
    if (chars.size != 3) return false
    return when {
        this.count { it == chars.first() } == 3 -> true
        this.count { it == chars.elementAt(1) } == 3 -> true
        this.count { it == chars.elementAt(2) } == 3 -> true
        else -> false
    }
}

private fun String.isTwoPair(): Boolean {
    val chars = this.toHashSet()
    if (chars.size != 3) return false
    return when {
        this.count { it == chars.first() } == 2 && this.count { it == chars.elementAt(1) } == 2 -> true
        this.count { it == chars.first() } == 2 && this.count { it == chars.elementAt(2) } == 2 -> true
        this.count { it == chars.elementAt(1) } == 2 && this.count { it == chars.elementAt(2) } == 2 -> true
        else -> false
    }
}

private fun String.isOnePair(): Boolean {
    val chars = this.toHashSet()
    if (chars.size != 4) return false
    return when {
        this.count { it == chars.first() } == 2 -> true
        this.count { it == chars.elementAt(1) } == 2 -> true
        this.count { it == chars.elementAt(2) } == 2 -> true
        this.count { it == chars.elementAt(3) } == 2 -> true
        else -> false
    }
}