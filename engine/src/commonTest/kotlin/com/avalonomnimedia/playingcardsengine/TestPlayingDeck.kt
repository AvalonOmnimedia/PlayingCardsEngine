package com.avalonomnimedia.playingcardsengine

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TestPlayingDeck {
    @Test
    fun whenPlayingDeckIsCreatedWith1Deck_ShouldHave52Cards() {
        val uut = PlayingDeck(Shuffler())
        val expected = 52

        uut.add(StandardDeck())
        val actual = uut.count()

        assertEquals(expected, actual)
    }

    @Test
    fun whenPlayingDeckIsCreatedWith2Decks_ShouldHave104Cards() {
        val uut = PlayingDeck(Shuffler())
        val expected = 104

        uut.add(StandardDeck())
        uut.add(StandardDeck())
        val actual = uut.count()

        assertEquals(expected, actual)
    }

    @Test
    fun whenPlayingDeckIsCreatedWith3Decks_ShouldHave156Cards() {
        val uut = PlayingDeck(Shuffler())
        val expected = 156

        uut.add(StandardDeck())
        uut.add(StandardDeck())
        uut.add(StandardDeck())
        val actual = uut.count()

        assertEquals(expected, actual)
    }

    @Test
    fun whenShuffled_CardsShouldBeInaDifferentOrder() {
        val uut = PlayingDeck(Shuffler())
        val standardDeck = StandardDeck()
        val expected = standardDeck.cards

        uut.add(standardDeck)
        uut.shuffle()
        val actual = uut.takeCards(uut.count())

        assertNotEquals(expected, actual)
    }

    @Test
    fun whenNumberOfCardsAskedForIs0_ShouldReturn0Cards() {
        val uut = PlayingDeck(Shuffler())
        uut.add(StandardDeck())
        val expected = 0

        val list = uut.takeCards(expected)
        val actual = list.count()

        assertEquals(expected, actual)
    }

    @Test
    fun whenNumberOfCardsAskedForIs1_ShouldReturn1Card() {
        val uut = PlayingDeck(Shuffler())
        uut.add(StandardDeck())
        val expected = 1

        val hand = uut.takeCards(expected)
        val actual = hand.count()

        assertEquals(expected, actual)
    }

    @Test
    fun whenNumberOfCardsAskedForIs4_ShouldReturn4Card() {
        val uut = PlayingDeck(Shuffler())
        uut.add(StandardDeck())
        val expected = 4

        val hand = uut.takeCards(expected)
        val actual = hand.count()

        assertEquals(expected, actual)
    }

    @Test
    fun whenNumberOfCardsAskedForIs52_ShouldReturn52Card() {
        val uut = PlayingDeck(Shuffler())
        uut.add(StandardDeck())
        val expected = 52

        val hand = uut.takeCards(expected)
        val actual = hand.count()

        assertEquals(expected, actual)
    }

    @Test
    fun whenaCardIsTaken_ShouldReturnFirstCard() {
        val uut = PlayingDeck(Shuffler())
        uut.add(StandardDeck())
        val expected = Card(Suit.DIAMONDS, Value.ACE)

        val actual = uut.takeCard()

        assertEquals(expected, actual)
    }

    @Test
    fun whenaCardIsTaken_ShouldRemoveItFromDeck() {
        val uut = PlayingDeck(Shuffler())
        uut.add(StandardDeck())
        val expected = 51

        uut.takeCard()
        val actual = uut.count()

        assertEquals(expected, actual)
    }
}