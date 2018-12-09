package com.avalonomnimedia.playingcardsengine

import kotlin.reflect.KClass

class PhaseDefinition<T: IGamePhase, C: GameContextBase>(
    val type: KClass<T>
) {
    internal val transitions = mutableMapOf<KClass<out IGameAction>, (C.() -> KClass<out IGamePhase>)>()   // Convert to HashMap with event as key
    internal val entryActions = mutableListOf<(GameContextBase) -> Unit>()
    internal val exitActions = mutableListOf<(GameContextBase) -> Unit>()

    fun <A : IGameAction> on(triggerType: KClass<A>, transition: (C.() -> KClass<out IGamePhase>)) {
        transitions[triggerType] = transition
    }

    inline fun <reified A: IGameAction> on(noinline transition: (C.() -> KClass<out IGamePhase>)) {
        on(A::class, transition)
    }

    inline fun <reified P: IGamePhase> transitionTo(): KClass<P> {
        return P::class
    }

    /**
     * Action performed by state on entry
     */
    fun onEntry(action: (GameContextBase) -> Unit) {
        entryActions.add(action)
    }

    /**
     * Enter the state and run all actions
     */
    fun enter(gameContext: C) {
        // Every action takes the current state
        entryActions.forEach { it(gameContext) }
    }

    /**
     * Action performed by state on exit
     */
    fun onExit(action: (GameContextBase) -> Unit) {
        exitActions.add(action)
    }

    /**
     * Enter the state and run all actions
     */
    fun exit(gameContext: C) {
        // Every action takes the current state
        exitActions.forEach { it(gameContext) }
    }

    /**
     * Get the appropriate transition for the [IGameAction]
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