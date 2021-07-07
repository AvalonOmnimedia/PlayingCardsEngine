package com.avalonomnimedia.playingcardsengine

import com.avalonomnimedia.playingcardsengine.Card
import com.avalonomnimedia.playingcardsengine.Deck
import com.avalonomnimedia.playingcardsengine.Shuffler
import kotlin.random.Random

actual class Shuffler actual constructor() {
    actual fun shuffle(cards: MutableList<Card>) {
        val random = Random(0)
        cards.sortBy { random.nextInt() }
    }
}

actual class Stack<T> actual constructor() {
    val stack = js("[];")
    actual fun isEmpty(): Boolean {
        return js("stack.count == 0;") as Boolean
    }

    actual fun isNotEmpty(): Boolean {
        return js("stack.count > 0;") as Boolean
    }

    actual fun push(item: T) {
        js("stack.push(item);")
    }

    actual fun peek(): T {
        return js("stack[stack.length-1];") as T
    }

    actual fun pop(): T {
        return js("stack.pop();") as T
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
        get() = TODO("Not yet implemented")
}

/**
 * [Deck] base class.  All decks must inherit from this.
 */
actual abstract class Deck {
    actual abstract val cards: List<Card>
}