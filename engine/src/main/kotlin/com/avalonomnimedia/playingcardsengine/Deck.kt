package com.avalonomnimedia.playingcardsengine

import java.util.Stack
import java.util.UUID

abstract class Deck {
    protected open val cards = ArrayList<Card>()

    fun add(deck: Deck) {
        cards.addAll(deck.cards)
    }
}

class PlayingDeck : Deck() {
    fun count() = cards.count()

    fun shuffle() {
        cards.sortBy { UUID.randomUUID() }
    }

    fun takeCard(): Card {
        return cards.removeAt(0)
    }

    fun takeCards(count: Int): List<Card> {
        val list = mutableListOf<Card>()
        for (x in 0 until count) {
            list.add(cards.removeAt(0))
        }
        return list
    }
}

class DiscardPile {
    private val stack = Stack<Card>()

    val isEmpty: Boolean
        get() = stack.isEmpty()

    val isNotEmpty: Boolean
        get() = stack.isNotEmpty()

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