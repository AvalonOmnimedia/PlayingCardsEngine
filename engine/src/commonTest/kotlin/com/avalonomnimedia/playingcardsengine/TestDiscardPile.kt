package com.avalonomnimedia.playingcardsengine

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Suppress("FunctionName")
class TestDiscardPile {
    @Test
    fun whenCardIsDiscarded_shouldBeOnTopOfPile() {
        val uut = DiscardPile()
        val expected = Card(Suit.CLUBS, Value.KING)
        uut.discard(expected)

        val actual = uut.peekTop()

        assertEquals(expected, actual)
    }

    @Test
    fun whenTopCardIsPeekedAt_shouldBeSameNextTime() {
        val uut = DiscardPile()
        val firstCard = Card(Suit.DIAMONDS, Value.QUEEN)
        val expected = Card(Suit.CLUBS, Value.KING)
        uut.discard(firstCard)
        uut.discard(expected)

        uut.peekTop()
        val actual = uut.peekTop()

        assertEquals(expected, actual)
    }

    @Test
    fun whenTopCardIsTaken_nextTimeShouldPullNextCard() {
        val uut = DiscardPile()
        val firstCard = Card(Suit.DIAMONDS, Value.QUEEN)
        val expected = Card(Suit.CLUBS, Value.KING)
        uut.discard(expected)
        uut.discard(firstCard)

        uut.takeTop()
        val actual = uut.takeTop()

        assertEquals(expected, actual)
    }

    @Test
    fun whenPileHasNoCards_isEmptyShouldBeTrue() {
        val uut = DiscardPile()

        val actual = uut.isEmpty

        assertTrue(actual)
    }

    @Test
    fun whenPileHasCards_isEmptyShouldBeFalse() {
        val uut = DiscardPile()
        val card = Card(Suit.DIAMONDS, Value.QUEEN)
        uut.discard(card)

        val actual = uut.isEmpty

        assertFalse(actual)
    }

    @Test
    fun whenPileHasNoCards_isNotEmptyShouldBeFalse() {
        val uut = DiscardPile()

        val actual = uut.isNotEmpty

        assertFalse(actual)
    }

    @Test
    fun whenPileHasCards_isNotEmptyShouldBeTrue() {
        val uut = DiscardPile()
        val card = Card(Suit.DIAMONDS, Value.QUEEN)
        uut.discard(card)

        val actual = uut.isNotEmpty

        assertTrue(actual)
    }
}