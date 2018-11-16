package com.avalonomnimedia.war.cli

import com.avalonomnimedia.playingcardsengine.Card
import com.avalonomnimedia.playingcardsengine.Deck
import com.avalonomnimedia.playingcardsengine.Suit
import com.avalonomnimedia.playingcardsengine.Value

class PracticeDeck() : Deck() {
    init {
        cards.add(Card(Suit.HEARTS, Value.ACE))
        cards.add(Card(Suit.HEARTS, Value.TWO))
        cards.add(Card(Suit.HEARTS, Value.THREE))
        cards.add(Card(Suit.HEARTS, Value.FOUR))
        cards.add(Card(Suit.HEARTS, Value.FIVE))
        cards.add(Card(Suit.HEARTS, Value.SIX))
        cards.add(Card(Suit.HEARTS, Value.SEVEN))
    }
}