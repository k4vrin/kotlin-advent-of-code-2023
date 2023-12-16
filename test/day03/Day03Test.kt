package day03

import readInputPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {
    
    private lateinit var actualInput: List<String>
    private lateinit var testInput1: List<String>
    private lateinit var testInput2: List<String>
    
    @BeforeTest
    fun setup() {
        actualInput = readInputPath("src/day03/day03.txt")
        testInput1 = readInputPath("src/day03/day03_test_1.txt")
        testInput2 = readInputPath("src/day03/day03_test_2.txt")
    }
    
    @Test
    fun `part 1, actual data, should be 536576`() {
        assertEquals(536576, day03.part1(actualInput))
    }
    
    @Test
    fun `part 1, test1 data, should be 13425`() {
        assertEquals(13425, day03.part1(testInput1))
    }
    
    @Test
    fun `part 1, test2 data, should be 925`() {
        assertEquals(925, day03.part1(testInput2))
    }
    
    @Test
    fun `part 2, actual data, should be 75741499`() {
        assertEquals(75741499, day03.part2(actualInput))
    }
    
    @Test
    fun `part 2, test1 data, should be 2709657`() {
        assertEquals(2709657, day03.part2(testInput1))
    }
    
    @Test
    fun `part 2, test2 data, should be 6756`() {
        assertEquals(6756, day03.part2(testInput2))
    }
}

