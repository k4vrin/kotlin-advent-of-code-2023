package day06

import readInputPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {

    private lateinit var actualInput: List<String>
    private lateinit var testInput: List<String>

    @BeforeTest
    fun setup() {
        actualInput = readInputPath("src/day06/day06.txt")
        testInput = readInputPath("src/day06/day06_test.txt")
    }

    @Test
    fun `part 1, actual data, should be 293046`() {
        assertEquals(293046, day06.part1(actualInput))
    }

    @Test
    fun `part 1, test data, should be 288`() {
        assertEquals(288, day06.part1(testInput))
    }


    @Test
    fun `part 2, actual data, should be 35150181`() {
        assertEquals(35150181, day06.part2(actualInput))
    }

    @Test
    fun `part 2, test data, should be 71503`() {
        assertEquals(71503, day06.part2(testInput))
    }
}