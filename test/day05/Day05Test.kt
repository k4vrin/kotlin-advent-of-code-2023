package day05

import readInputPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Test {

    private lateinit var actualInput: List<String>
    private lateinit var testInput: List<String>

    @BeforeTest
    fun setup() {
        actualInput = readInputPath("src/day05/day05.txt")
        testInput = readInputPath("src/day05/day05_test.txt")
    }

    @Test
    fun `part 1, actual data, should be 1181555926`() {
        assertEquals(1181555926, day05.part1(actualInput))
    }

    @Test
    fun `part 1, test data, should be 35`() {
        assertEquals(35, day05.part1(testInput))
    }


    @Test
    fun `part 2, actual data, should be 37806486`() {
        assertEquals(37806486, day05.part2(actualInput))
    }

    @Test
    fun `part 2, test data, should be 46`() {
        assertEquals(46, day05.part2(testInput))
    }
}