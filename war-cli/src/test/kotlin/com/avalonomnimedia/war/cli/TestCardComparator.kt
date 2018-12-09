package com.avalonomnimedia.war.cli

import com.avalonomnimedia.playingcardsengine.Card
import com.avalonomnimedia.playingcardsengine.Suit
import com.avalonomnimedia.playingcardsengine.Value
import org.junit.Assert
import org.junit.Test

class TestCardComparator {
    @Test
    fun `when compare is called and value of card1 is greater than card2, should return 1`() {
        val expected = 1
        val card1 = Card(Suit.CLUBS, Value.KING)
        val card2 = Card(Suit.CLUBS, Value.JACK)

        val uut = CardComparator()
        val actual = uut.compare(card1, card2)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when compare is called and value of card1 is less than card2, should return -1`() {
        val expected = -1
        val card1 = Card(Suit.CLUBS, Value.JACK)
        val card2 = Card(Suit.CLUBS, Value.KING)

        val uut = CardComparator()
        val actual = uut.compare(card1, card2)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when compare is called and value of card1 is equal to card2, should return 0`() {
        val expected = 0
        val card1 = Card(Suit.CLUBS, Value.JACK)
        val card2 = Card(Suit.CLUBS, Value.JACK)

        val uut = CardComparator()
        val actual = uut.compare(card1, card2)

        Assert.assertEquals(expected, actual)
    }
}