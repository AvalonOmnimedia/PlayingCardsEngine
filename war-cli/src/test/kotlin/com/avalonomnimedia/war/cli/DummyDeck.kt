package com.avalonomnimedia.war.cli

import com.avalonomnimedia.playingcardsengine.Card
import com.avalonomnimedia.playingcardsengine.Deck
import com.avalonomnimedia.playingcardsengine.Suit
import com.avalonomnimedia.playingcardsengine.Value

class PracticeDeck : Deck() {
    override val cards: List<Card>
    init {
        val newCards = mutableListOf<Card>()
        newCards.add(Card(Suit.HEARTS, Value.ACE))
        newCards.add(Card(Suit.HEARTS, Value.TWO))
        newCards.add(Card(Suit.HEARTS, Value.THREE))
        newCards.add(Card(Suit.HEARTS, Value.FOUR))
        newCards.add(Card(Suit.HEARTS, Value.FIVE))
        newCards.add(Card(Suit.HEARTS, Value.SIX))
        newCards.add(Card(Suit.HEARTS, Value.SEVEN))
        cards = newCards.toList()
    }
}

class FourCardDeck(suit: Suit = Suit.HEARTS) : Deck() {
    override val cards: List<Card>
    init {
        val newCards = mutableListOf<Card>()
        newCards.add(Card(suit, Value.ACE))
        newCards.add(Card(suit, Value.TWO))
        newCards.add(Card(suit, Value.THREE))
        newCards.add(Card(suit, Value.FOUR))
        cards = newCards.toList()
    }
}