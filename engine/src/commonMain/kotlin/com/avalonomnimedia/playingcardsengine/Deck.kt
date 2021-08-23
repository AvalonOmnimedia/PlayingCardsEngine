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
abstract class Deck {
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
class PlayingDeck(private val shuffler: Shuffler) {
    private var cards = mutableListOf<Card>()
    fun count() = cards.count()

    fun shuffle() {
        shuffler.shuffle(cards)
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

    fun add(deck: Deck) {
        cards.addAll(deck.cards)
    }
}

/**
 * Standard 52 card french [deck][Deck].
 */
class StandardDeck : Deck() {
    override val cards: List<Card>

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