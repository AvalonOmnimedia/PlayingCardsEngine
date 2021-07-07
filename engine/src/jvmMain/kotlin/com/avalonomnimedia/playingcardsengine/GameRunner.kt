package com.avalonomnimedia.playingcardsengine

/**
 * Create a new [GameRunner] with a [gameContext] of type [C] and an [initialState].
 */
actual fun <C: GameContextBase> createRunner(gameContext: C, initialState: IGamePhase, init: GameRunner<C>.() -> Unit): GameRunner<C> {
    val runner = GameRunner(gameContext, initialState)
    init(runner)
    return runner
}