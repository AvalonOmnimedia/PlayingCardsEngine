package com.avalonomnimedia.playingcardsengine

import kotlin.test.Test
import kotlin.test.assertEquals

class TestFaceUpHand {
    @Test
    fun whenHandIsCreated_countShouldReturn0() {
        val expected = 0

        val uut = FaceUpHand()
        val actual = uut.count()

        assertEquals(expected, actual)
    }

    @Test
    fun whenHandIsGiven1Card_CountShouldReturn1() {
        val expected = 1

        val uut = FaceUpHand()
        uut.add(Card(Suit.HEARTS, Value.SEVEN))
        val actual = uut.count()

        assertEquals(expected, actual)
    }

    @Test
    fun whenHandIsGiven2Cards_CountShouldReturn2() {
        val expected = 2

        val uut = FaceUpHand()
        uut.add(Card(Suit.HEARTS, Value.SEVEN))
        uut.add(Card(Suit.HEARTS, Value.SEVEN))
        val actual = uut.count()

        assertEquals(expected, actual)
    }

    @Test
    fun whenHandIsGivenCard_ShouldBeInListOfCards() {
        val expected = Card(Suit.HEARTS, Value.SEVEN)

        val uut = FaceUpHand()
        uut.add(expected)
        val actual = uut.cards.first()

        assertEquals(expected, actual)
    }

    @Test
    fun whenHandIsGivenMultipleCards_TheyShouldAllBeInListOfCards() {
        val card1 = Card(Suit.HEARTS, Value.SEVEN)
        val card2 = Card(Suit.CLUBS, Value.SIX)
        val card3 = Card(Suit.DIAMONDS, Value.FOUR)
        val expected = listOf(card1, card2, card3)

        val uut = FaceUpHand()
        uut.add(card1)
        uut.add(card2)
        uut.add(card3)
        val actual = uut.cards

        assertEquals(expected, actual)
    }

    @Test
    fun whenHandIsInitializeWithCards_TheyShouldAllBeInListOfCards() {
        val card1 = Card(Suit.HEARTS, Value.SEVEN)
        val card2 = Card(Suit.CLUBS, Value.SIX)
        val card3 = Card(Suit.DIAMONDS, Value.FOUR)
        val expected = listOf(card1, card2, card3)

        val uut = FaceUpHand(expected)
        val actual = uut.cards

        assertEquals(expected, actual)
    }
}