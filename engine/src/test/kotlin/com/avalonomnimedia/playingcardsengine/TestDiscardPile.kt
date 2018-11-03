package com.avalonomnimedia.playingcardsengine

import org.junit.Assert
import org.junit.Test

class TestDiscardPile {
    @Test
    fun `when card is discarded, should be on top of pile`() {
        val discardPile = DiscardPile()
        val expected = Card(Suit.CLUBS, Value.KING)
        discardPile.discard(expected)

        val actual = discardPile.peekTop()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when top card is peeked at, should be same next time`() {
        val discardPile = DiscardPile()
        val firstCard = Card(Suit.DIAMONDS, Value.QUEEN)
        val expected = Card(Suit.CLUBS, Value.KING)
        discardPile.discard(firstCard)
        discardPile.discard(expected)

        discardPile.peekTop()
        val actual = discardPile.peekTop()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when top card is taken, next time should pull next card`() {
        val discardPile = DiscardPile()
        val firstCard = Card(Suit.DIAMONDS, Value.QUEEN)
        val expected = Card(Suit.CLUBS, Value.KING)
        discardPile.discard(expected)
        discardPile.discard(firstCard)

        discardPile.takeTop()
        val actual = discardPile.takeTop()

        Assert.assertEquals(expected, actual)
    }
}