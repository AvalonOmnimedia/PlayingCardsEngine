package com.avalonomnimedia.playingcardsengine

import java.util.UUID

actual class Shuffler actual constructor() {
    actual fun shuffle(cards: MutableList<Card>) {
        cards.sortBy { UUID.randomUUID() }
    }
}

actual class Stack<T> actual constructor() : java.util.ArrayDeque<T>() {
    actual override fun isEmpty(): Boolean {
        return super.isEmpty()
    }

    actual fun isNotEmpty(): Boolean {
        return !super.isEmpty()
    }

    actual override fun push(item: T) {
        super.push(item)
    }

    actual override fun peek(): T {
        return super.peek()
    }

    actual override fun pop(): T {
        return super.pop()
    }
}