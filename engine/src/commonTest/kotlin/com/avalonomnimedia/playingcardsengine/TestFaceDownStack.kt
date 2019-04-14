package com.avalonomnimedia.playingcardsengine

import kotlin.test.Test
import kotlin.test.assertEquals

class TestFaceDownStack {
    @Test
    fun whenHandIsInitializeWithCards_theyShouldReturnedInThatOrderWhenTakenOffTheTop() {
        val card1 = Card(Suit.HEARTS, Value.SEVEN)
        val card2 = Card(Suit.CLUBS, Value.SIX)
        val card3 = Card(Suit.DIAMONDS, Value.FOUR)
        val expected = listOf(card1, card2, card3)

        val uut = FaceDownStack(expected)
        val actual = listOf(uut.takeTop(), uut.takeTop(), uut.takeTop())

        assertEquals(expected, actual)
    }

    @Test
    fun whenCardIsAddedToBottomOfEmptyStack_shouldBeTheFirstOneReturnedOffTheTop() {
        val expected = Card(Suit.HEARTS, Value.SEVEN)

        val uut = FaceDownStack()
        uut.addToBottom(expected)
        val actual = uut.takeTop()

        assertEquals(expected, actual)
    }

    @Test
    fun whenCardsAreAddedToBottomOfStack_theyShouldReturnedInThatOrderWhenTakenOffTheTop() {
        val card1 = Card(Suit.HEARTS, Value.SEVEN)
        val card2 = Card(Suit.CLUBS, Value.SIX)
        val card3 = Card(Suit.DIAMONDS, Value.FOUR)
        val expected = listOf(card1, card2, card3)

        val uut = FaceDownStack(expected)
        uut.addToBottom(card1)
        uut.addToBottom(card2)
        uut.addToBottom(card3)
        val actual = listOf(uut.takeTop(), uut.takeTop(), uut.takeTop())

        assertEquals(expected, actual)
    }
}