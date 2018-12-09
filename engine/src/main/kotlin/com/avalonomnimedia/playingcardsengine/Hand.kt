package com.avalonomnimedia.playingcardsengine

/**
 * [Hand] base class.  All hands must inherit from this.
 */
abstract class Hand(initialCards: List<Card> = listOf()) {
    @Suppress("PropertyName", "This is an internal property.")
    protected val _cards = mutableListOf<Card>()

    init {
        _cards.addAll(initialCards)
    }

    fun count(): Int {
        return _cards.count()
    }
}

class FaceUpHand(initialCards: List<Card> = listOf()) : Hand(initialCards) {
    val cards: List<Card> = _cards

    fun add(card: Card) {
        _cards.add(card)
    }

    override fun toString(): String {
        return _cards.toString()
    }
}

class FaceDownStack(initialCards: List<Card> = listOf()) : Hand(initialCards) {
    fun addToBottom(card: Card) {
        _cards.add(card)
    }

    fun takeTop(): Card {
        return _cards.removeAt(0)
    }

    override fun toString(): String {
        return _cards.toString()
    }
}