package com.avalonomnimedia.mayi

import com.avalonomnimedia.playingcardsengine.PlayingDeck
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import java.util.LinkedList
import java.util.Queue

class TestGameContext {
    @Test(expected = MissingRoundException::class)
    fun `if no rounds throw exception`() {
        val rounds: Queue<Round> = LinkedList()
        GameContext(mockk(), mockk(), mockk(), rounds)
    }

    @Test
    fun `player should have 10 cards after deal`() {
        val player = MayIPlayer("1")
        val players = listOf(player)
        val deck = mockk<PlayingDeck>()
        val dealAmount = 10
        val rounds: Queue<Round> = LinkedList(listOf(Round(1, dealAmount)))

        every { deck.takeCard() } returns(mockk())

        val uut = GameContext(players, mockk(), deck, rounds)
        uut.onDeal()

        Assert.assertEquals(player.hand?.count(), dealAmount)
    }

    @Test
    fun `all players should have 10 cards after deal`() {
        val players = listOf(MayIPlayer("1"), MayIPlayer("2"), MayIPlayer("2"), MayIPlayer("4"))
        val deck = mockk<PlayingDeck>()
        val dealAmount = 10
        val rounds: Queue<Round> = LinkedList(listOf(Round(1, dealAmount)))

        every { deck.takeCard() } returns(mockk())

        val uut = GameContext(players, mockk(), deck, rounds)
        uut.onDeal()

        for (player in players) {
            Assert.assertEquals("Player ${player.name} has the wrong number of cards.",
                player.hand?.count(), dealAmount)
        }
    }
}