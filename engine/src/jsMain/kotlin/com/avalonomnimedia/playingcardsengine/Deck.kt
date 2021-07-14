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