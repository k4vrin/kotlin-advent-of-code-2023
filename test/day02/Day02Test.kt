package day02

import readInputPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test {
    
    private lateinit var actualInput: List<String>
    private lateinit var testInput: List<String>
    
    @BeforeTest
    fun setup() {
        actualInput = readInputPath("src/day02/day02.txt")
        testInput = readInputPath("src/day02/day02_test.txt")
    }
    
    @Test
    fun `part 1, actual data, should be 1734`() {
        assertEquals(1734, day02.part1(actualInput))
    }
    
    @Test
    fun `part 1, test data, should be 8`() {
        assertEquals(8, day02.part1(testInput))
    }
    
    @Test
    fun `part 2, actual data, should be 70387`() {
        assertEquals(70387, day02.part2(actualInput))
    }
    
    @Test
    fun `part 2, test data, should be 2286`() {
        assertEquals(2286, day02.part2(testInput))
    }
}

