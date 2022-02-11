package com.avalonomnimedia.playingcardsengine

data class Card(val suit: Suit, val value: Value) {
    override fun toString(): String {
        return "$value$suit"
    }
}

enum class Suit(val symbol: String) {
    DIAMONDS("♦️"),
    SPADES("♠️"),
    CLUBS("♣️"),
    HEARTS("♥️");

    override fun toString(): String {
        return symbol
    }
}

enum class Value(val value: String) {
    ACE("A"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K");

    override fun toString(): String {
        return value
    }
}