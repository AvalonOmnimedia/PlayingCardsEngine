package com.avalonomnimedia.mayi

import io.mockk.mockk
import org.junit.Test
import java.util.LinkedList
import java.util.Queue

class TestGameContext {
    @Test(expected = MissingRoundException::class)
    fun `if no rounds throw exception`() {
        val rounds: Queue<Round> = LinkedList()
        GameContext(mockk(), mockk(), mockk(), rounds)
    }
}