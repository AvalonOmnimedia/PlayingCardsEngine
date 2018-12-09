package com.avalonomnimedia.war.cli

import com.avalonomnimedia.playingcardsengine.Suit
import org.junit.Assert
import org.junit.Test

class TestBattle {
    @Test
    fun `when takeAllCards is called, all cards from player1Stack should be returned`() {
        val expected = FourCardDeck().cards

        val uut = Battle()

        for(card in FourCardDeck().cards.reversed()) uut.player1Stack.discard(card)

        val actual = uut.takeAllCards()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when takeAllCards is called, all cards from player2Stack should be returned`() {
        val expected = FourCardDeck().cards

        val uut = Battle()

        for(card in FourCardDeck().cards.reversed()) uut.player2Stack.discard(card)

        val actual = uut.takeAllCards()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when takeAllCards is called, all cards from player1Stack and player2Stack should be returned`() {
        val expected = FourCardDeck(Suit.HEARTS).cards + FourCardDeck(Suit.CLUBS).cards

        val uut = Battle()

        for(card in FourCardDeck(Suit.HEARTS).cards.reversed()) uut.player1Stack.discard(card)
        for(card in FourCardDeck(Suit.CLUBS).cards.reversed()) uut.player2Stack.discard(card)

        val actual = uut.takeAllCards()

        Assert.assertEquals(expected, actual)
    }
}