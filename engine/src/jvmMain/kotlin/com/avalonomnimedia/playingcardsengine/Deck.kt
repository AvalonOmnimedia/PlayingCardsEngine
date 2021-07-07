package com.avalonomnimedia.playingcardsengine

import java.util.UUID

actual class Shuffler actual constructor() {
    actual fun shuffle(cards: MutableList<Card>) {
        cards.sortBy { UUID.randomUUID() }
    }
}

actual class Stack<T> actual constructor() : java.util.ArrayDeque<T>() {
    actual override fun isEmpty(): Boolean {
        return super.isEmpty()
    }

    actual fun isNotEmpty(): Boolean {
        return !super.isEmpty()
    }

    actual override fun push(item: T) {
        super.push(item)
    }

    actual override fun peek(): T {
        return super.peek()
    }

    actual override fun pop(): T {
        return super.pop()
    }
}

/**
 * The [Deck] used for game play.
 *
 * Must be populated with other [decks][Deck].
 */
actual class PlayingDeck actual constructor(private val shuffler: Shuffler) {
    private var cards = mutableListOf<Card>()
    actual fun count() = cards.count()

    actual fun shuffle() {
        shuffler.shuffle(cards)
    }

    actual fun takeCard(): Card {
        return cards.removeAt(0)
    }

    actual fun takeCards(count: Int): List<Card> {
        val list = mutableListOf<Card>()
        for (x in 0 until count) {
            list.add(cards.removeAt(0))
        }
        return list
    }

    actual fun add(deck: Deck) {
        cards.addAll(deck.cards)
    }
}

/**
 * Standard 52 card french [deck][Deck].
 */
actual class StandardDeck : Deck() {
    actual override val cards: List<Card>

    init {
        val newCards = mutableListOf<Card>()
        Suit.values().forEach { suit ->
            Value.values().forEach { value ->
                newCards.add(Card(suit, value))
            }
        }

        cards = newCards.toList()
    }
}

/**
 * [Deck] base class.  All decks must inherit from this.
 */
actual abstract class Deck {
    actual abstract val cards: List<Card>
}