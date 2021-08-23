package com.avalonomnimedia.playingcardsengine

/**
 *  Player with a [Hand] of type [T].
 *
 *  All players must inherit from this.
 */
abstract class PlayerBase<T : Hand>(val name: String) {
    var hand: T? = null

    override fun toString(): String {
        return name
    }
}