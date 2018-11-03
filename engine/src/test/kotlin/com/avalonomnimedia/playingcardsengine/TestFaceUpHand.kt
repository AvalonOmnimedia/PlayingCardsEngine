package com.avalonomnimedia.playingcardsengine

import org.junit.Assert
import org.junit.Test

class TestFaceUpHand {
    @Test
    fun `when hand is created, count() should return 0`() {
        val expected = 0

        val uut = FaceUpHand()
        val actual = uut.count()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when hand is given 1 card, count() should return 1`() {
        val expected = 1

        val uut = FaceUpHand()
        uut.add(Card(Suit.HEARTS, Value.SEVEN))
        val actual = uut.count()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when hand is given 2 cards, count() should return 2`() {
        val expected = 2

        val uut = FaceUpHand()
        uut.add(Card(Suit.HEARTS, Value.SEVEN))
        uut.add(Card(Suit.HEARTS, Value.SEVEN))
        val actual = uut.count()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when hand is given card, should be in list of cards`() {
        val expected = Card(Suit.HEARTS, Value.SEVEN)

        val uut = FaceUpHand()
        uut.add(expected)
        val actual = uut.cards.first()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when hand is given multiple cards, they should all be in list of cards`() {
        val card1 = Card(Suit.HEARTS, Value.SEVEN)
        val card2 = Card(Suit.CLUBS, Value.SIX)
        val card3 = Card(Suit.DIAMONDS, Value.FOUR)
        val expected = listOf(card1, card2, card3)

        val uut = FaceUpHand()
        uut.add(card1)
        uut.add(card2)
        uut.add(card3)
        val actual = uut.cards

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when hand is initialize with cards, they should all be in list of cards`() {
        val card1 = Card(Suit.HEARTS, Value.SEVEN)
        val card2 = Card(Suit.CLUBS, Value.SIX)
        val card3 = Card(Suit.DIAMONDS, Value.FOUR)
        val expected = listOf(card1, card2, card3)

        val uut = FaceUpHand(expected)
        val actual = uut.cards

        Assert.assertEquals(expected, actual)
    }
}