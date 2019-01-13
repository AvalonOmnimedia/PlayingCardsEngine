package com.avalonomnimedia.playingcardsengine

import org.junit.Assert
import org.junit.Test

class TestFaceDownStack {
    @Test
    fun `when hand is initialize with cards, they should returned in that order when taken off the top`() {
        val card1 = Card(Suit.HEARTS, Value.SEVEN)
        val card2 = Card(Suit.CLUBS, Value.SIX)
        val card3 = Card(Suit.DIAMONDS, Value.FOUR)
        val expected = listOf(card1, card2, card3)

        val uut = FaceDownStack(expected)
        val actual = listOf(uut.takeTop(), uut.takeTop(), uut.takeTop())

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when card is added to bottom of empty stack, should be the first one returned off the top`() {
        val expected = Card(Suit.HEARTS, Value.SEVEN)

        val uut = FaceDownStack()
        uut.addToBottom(expected)
        val actual = uut.takeTop()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when cards are added to bottom of stack, they should returned in that order when taken off the top`() {
        val card1 = Card(Suit.HEARTS, Value.SEVEN)
        val card2 = Card(Suit.CLUBS, Value.SIX)
        val card3 = Card(Suit.DIAMONDS, Value.FOUR)
        val expected = listOf(card1, card2, card3)

        val uut = FaceDownStack(expected)
        uut.addToBottom(card1)
        uut.addToBottom(card2)
        uut.addToBottom(card3)
        val actual = listOf(uut.takeTop(), uut.takeTop(), uut.takeTop())

        Assert.assertEquals(expected, actual)
    }
}