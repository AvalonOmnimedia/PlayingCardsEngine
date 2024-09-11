package com.avalonomnimedia.playingcardsengine

import kotlin.reflect.KClass

/**
 * Create a new [GameRunner] with a [gameContext] of type [C] and an [initialState].
 */
fun <C: GameContextBase> createRunner(gameContext: C, initialState: IGamePhase, init: GameRunner<C>.() -> Unit): GameRunner<C> {
    val runner = GameRunner(gameContext, initialState)
    init(runner)
    return runner
}

class GameRunner<C: GameContextBase>
internal constructor(
    val gameContext: C,
    var currentPhase: IGamePhase
) {
    internal val phaseDefinitions: MutableList<PhaseDefinition<out IGamePhase, C>> = mutableListOf()

    fun <P: IGamePhase> phase(phaseType: KClass<P>, init: PhaseDefinition<out P, C>.() -> Unit) {
        val phaseDefinition = PhaseDefinition<P, C>(phaseType)
        init(phaseDefinition)
        phaseDefinitions.add(phaseDefinition)
    }

    /**
     * Register a [IGamePhase] on the [GameRunner]
     */
    inline fun <reified P: IGamePhase> phase(noinline init: PhaseDefinition<out P, C>.() -> Unit) {
        phase(P::class, init)
    }

    /**
     * Perform an [action] on the [current phase][currentPhase].
     */
    fun perform(action: IGameAction) {
        val definition = phaseDefinitions.currentPhase()
        definition.exit(gameContext)
        currentPhase = definition.getTransitionForAction(action::class)(gameContext).java.getDeclaredConstructor().newInstance()
        val newDefinition = phaseDefinitions.currentPhase()
        newDefinition.enter(gameContext)
    }

    private fun List<PhaseDefinition<*, C>>.currentPhase(): PhaseDefinition<*, C> {
        return singleOrNull { it.type.isInstance(currentPhase) } ?: throw PhaseNotDefinedException(currentPhase, this)
    }
}

class PhaseNotDefinedException(
    expectedPhase: IGamePhase,
    list: List<PhaseDefinition<out IGamePhase, out GameContextBase>>
) : Exception() {
    override val message: String? =
        "Phase ${expectedPhase::class.simpleName} has not been defined. Possible phases are:\n${list.map { it.type.simpleName }}"
}

interface IGamePhase
interface IGameAction

interface IGameStart
interface IRoundStart
interface IRoundEnd
interface IGameEnd