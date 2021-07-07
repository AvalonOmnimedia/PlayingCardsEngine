package com.avalonomnimedia.playingcardsengine

expect class Shuffler() {
    fun shuffle(cards: MutableList<Card>)
}

expect class Stack<T>() {
    fun isEmpty(): Boolean
    fun isNotEmpty(): Boolean
    fun push(item: T)
    fun peek(): T
    fun pop(): T
}

/**
 * [Deck] base class.  All decks must inherit from this.
 */
expect abstract class Deck {
     abstract val cards: List<Card>
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

/**
 * The [Deck] used for game play.
 *
 * Must be populated with other [decks][Deck].
 */
expect class PlayingDeck(shuffler: Shuffler) {
    fun count(): Int
    fun shuffle()
    fun takeCard(): Card
    fun takeCards(count: Int): List<Card>
    fun add(deck: Deck)
}

/**
 * Standard 52 card french [deck][Deck].
 */
expect class StandardDeck : Deck {
    override val cards: List<Card>
}