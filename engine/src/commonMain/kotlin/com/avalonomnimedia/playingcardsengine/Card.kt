package com.avalonomnimedia.playingcardsengine

data class Card(val suit: Suit, val value: Value) {
    override fun toString(): String {
        return "$value of $suit"
    }
}

enum class Suit {
    DIAMONDS,
    SPADES,
    CLUBS,
    HEARTS
}

enum class Value {
    ACE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING
}