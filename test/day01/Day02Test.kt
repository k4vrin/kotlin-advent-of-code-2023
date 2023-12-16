package day01

import readInputPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {
    
    private lateinit var actualInput: List<String>
    private lateinit var testInput1: List<String>
    private lateinit var testInput2: List<String>
    
    @BeforeTest
    fun setup() {
        actualInput = readInputPath("src/day01/day01.txt")
        testInput1 = readInputPath("src/day01/day01_test1.txt")
        testInput2 = readInputPath("src/day01/day01_test2.txt")
    }
    
    @Test
    fun `part 1, actual data, should be 54644`() {
        assertEquals(54644, day01.part1(actualInput))
    }
    
    @Test
    fun `part 1, test data, should be 142`() {
        assertEquals(142, day01.part1(testInput1))
    }
    
    @Test
    fun `part 2, actual data, should be 53348`() {
        assertEquals(53348, day01.part2(actualInput))
    }
    
    @Test
    fun `part 2, test data, should be 281`() {
        assertEquals(281, day01.part2(testInput2))
    }
}

