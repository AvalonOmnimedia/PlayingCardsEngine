package com.avalonomnimedia.playingcardsengine

import java.util.Stack
import java.util.UUID

sealed class Deck {
    protected val cards = ArrayList<Card>()

    protected fun add(deck: Deck) {
        cards.addAll(deck.cards)
    }
}

class PlayingDeck : Deck() {
    fun count() = cards.count()

    fun add(deck: StandardDeck) {
        super.add(deck)
    }

    fun shuffle() {
        cards.sortBy { UUID.randomUUID() }
    }

    fun takeHand(numberOfCards: Int): Hand {
        return Hand(cards.take(numberOfCards))
    }
}

class DiscardPile {
    private val stack = Stack<Card>()

    fun discard(card: Card) {
        stack.push(card)
    }

    fun peekTop(): Card {
        return stack.peek()
    }

    fun takeTop(): Card {
        return stack.pop()
    }
}

class StandardDeck : Deck() {
    init {
        Suit.values().forEach { suit ->
            Value.values().forEach { value ->
                cards.add(Card(suit, value))
            }
        }
    }
}

class Hand(val cards: List<Card> = listOf()) {
    override fun toString(): String {
        return cards.toString()
    }
}

data class Card(val suit: Suit, val value: Value)