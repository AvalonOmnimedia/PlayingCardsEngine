package com.avalonomnimedia.playingcardsengine

import kotlin.reflect.KClass

actual fun <T : Any> KClass<T>.construct(): T {
        return constructors.first().call()
}