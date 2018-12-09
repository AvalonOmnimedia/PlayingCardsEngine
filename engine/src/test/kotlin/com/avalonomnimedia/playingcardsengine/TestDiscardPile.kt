package com.avalonomnimedia.playingcardsengine

import org.junit.Assert
import org.junit.Test

class TestDiscardPile {
    @Test
    fun `when card is discarded, should be on top of pile`() {
        val uut = DiscardPile()
        val expected = Card(Suit.CLUBS, Value.KING)
        uut.discard(expected)

        val actual = uut.peekTop()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when top card is peeked at, should be same next time`() {
        val uut = DiscardPile()
        val firstCard = Card(Suit.DIAMONDS, Value.QUEEN)
        val expected = Card(Suit.CLUBS, Value.KING)
        uut.discard(firstCard)
        uut.discard(expected)

        uut.peekTop()
        val actual = uut.peekTop()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when top card is taken, next time should pull next card`() {
        val uut = DiscardPile()
        val firstCard = Card(Suit.DIAMONDS, Value.QUEEN)
        val expected = Card(Suit.CLUBS, Value.KING)
        uut.discard(expected)
        uut.discard(firstCard)

        uut.takeTop()
        val actual = uut.takeTop()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when pile has no cards, isEmpty should be true`() {
        val uut = DiscardPile()

        val actual = uut.isEmpty

        Assert.assertTrue(actual)
    }

    @Test
    fun `when pile has cards, isEmpty should be false`() {
        val uut = DiscardPile()
        val card = Card(Suit.DIAMONDS, Value.QUEEN)
        uut.discard(card)

        val actual = uut.isEmpty

        Assert.assertFalse(actual)
    }

    @Test
    fun `when pile has no cards, isNotEmpty should be false`() {
        val uut = DiscardPile()

        val actual = uut.isNotEmpty

        Assert.assertFalse(actual)
    }

    @Test
    fun `when pile has cards, isNotEmpty should be true`() {
        val uut = DiscardPile()
        val card = Card(Suit.DIAMONDS, Value.QUEEN)
        uut.discard(card)

        val actual = uut.isNotEmpty

        Assert.assertTrue(actual)
    }
}