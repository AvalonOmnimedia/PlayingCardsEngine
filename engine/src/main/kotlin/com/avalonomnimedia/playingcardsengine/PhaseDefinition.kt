package com.avalonomnimedia.playingcardsengine

import kotlin.reflect.KClass

/**
 * The metadata that the [GameRunner] keeps about a particular [IGamePhase].
 */
class PhaseDefinition<T: IGamePhase, C: GameContextBase>(
    val type: KClass<T>
) {
    internal val transitions = mutableMapOf<KClass<out IGameAction>, (C.() -> KClass<out IGamePhase>)>()   // Convert to HashMap with event as key
    internal val entryActions = mutableListOf<(GameContextBase) -> Unit>()
    internal val exitActions = mutableListOf<(GameContextBase) -> Unit>()

    fun <A : IGameAction> on(triggerType: KClass<A>, transition: (C.() -> KClass<out IGamePhase>)) {
        transitions[triggerType] = transition
    }

    /**
     * Used to transition to another [IGamePhase] for a particular [IGameAction].
     */
    inline fun <reified A: IGameAction> on(noinline transition: (C.() -> KClass<out IGamePhase>)) {
        on(A::class, transition)
    }

    /**
     * Helper method for getting the [KClass] for a [IGamePhase].
     */
    inline fun <reified P: IGamePhase> transitionTo(): KClass<P> {
        return P::class
    }

    /**
     * Function performed by phase on entry.
     */
    fun onEntry(action: (GameContextBase) -> Unit) {
        entryActions.add(action)
    }

    /**
     * Enter the phase and run all functions.
     */
    fun enter(gameContext: C) {
        // Every action takes the current state
        entryActions.forEach { it(gameContext) }
    }

    /**
     * Function performed by phase on exit.
     */
    fun onExit(action: (GameContextBase) -> Unit) {
        exitActions.add(action)
    }

    /**
     * Exit the phase and run all functions.
     */
    fun exit(gameContext: C) {
        // Every action takes the current state
        exitActions.forEach { it(gameContext) }
    }

    /**
     * Get the appropriate transition for the [action]
     */
    fun getTransitionForAction(action: KClass<out IGameAction>): (C.() -> KClass<out IGamePhase>) {
            return transitions[action] ?: throw ActionNotRegisteredException(
                action,
                this
            )
    }
}

class ActionNotRegisteredException(triedAction: KClass<out IGameAction>, currentPhase: PhaseDefinition<out IGamePhase, out GameContextBase>) : Exception() {
    override val message: String? =
        "Action $triedAction has not been registered on current phase $currentPhase."
}