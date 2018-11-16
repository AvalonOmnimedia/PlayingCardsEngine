package com.avalonomnimedia.playingcardsengine

abstract class PlayerBase<T : Hand>(val name: String) {
    var hand: T? = null

    override fun toString(): String {
        return name
    }
}