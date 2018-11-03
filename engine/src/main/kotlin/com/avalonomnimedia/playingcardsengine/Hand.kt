package com.avalonomnimedia.playingcardsengine

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