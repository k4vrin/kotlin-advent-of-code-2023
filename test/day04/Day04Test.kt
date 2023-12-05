package day04

import readInputPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Test {
    
    private lateinit var acctualInput: List<String>
    private lateinit var testInput: List<String>
    
    @BeforeTest
    fun setup() {
        acctualInput = readInputPath("src/day04/day04.txt")
        testInput = readInputPath("src/day04/day04_test.txt")
    }
    
    @Test
    fun `part 1, acctual data, should be 18519`() {
        assertEquals(18519, day04.part1(acctualInput))
    }
    
    @Test
    fun `part 1, test data, should be 13`() {
        assertEquals(13, day04.part1(testInput))
    }
    
    
    @Test
    fun `part 2, acctual data, should be 11787590`() {
        assertEquals(11787590, day04.part2(acctualInput))
    }
    
    @Test
    fun `part 2, test data, should be 30`() {
            assertEquals(30, day04.part2(testInput))
    }
    
    
}

