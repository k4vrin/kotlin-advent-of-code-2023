package day07

import day06.part1
import day06.part2
import readInputPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class Day07Test {
    private lateinit var actualInput: List<String>
    private lateinit var testInput: List<String>

    @BeforeTest
    fun setup() {
        actualInput = readInputPath("src/day07/day07.txt")
        testInput = readInputPath("src/day07/day07_test.txt")
    }

    @Test
    fun `part 1, actual data, should be 256448566`() {
        assertEquals(256448566, Day07.part1(actualInput))
    }

    @Test
    fun `part 1, test data, should be 6440`() {
        assertEquals(6440, Day07.part1(testInput))
    }


    @Test
    fun `part 2, actual data, should be 254412181`() {
        assertEquals(254412181, Day07.part2(actualInput))
    }

    @Test
    fun `part 2, test data, should be 5905`() {
        assertEquals(5905, Day07.part2(testInput))
    }
}