package com.avalonomnimedia.playingcardsengine

abstract class GameBase {
    abstract val players: List<PlayerBase<out Hand>>
    abstract val cardComparator: Comparator<Card>
    abstract val deck: PlayingDeck
    abstract var currentPhase: GamePhaseBase
    abstract fun play()
}

abstract class GamePhaseBase {
    abstract fun perform(action: GameActionBase): GamePhaseBase
}

abstract class GameActionBase

interface IGameStart
interface IRoundStart
interface IRoundEnd
interface IGameEnd

class InvalidActionException(currentPhase: GamePhaseBase) : Exception() {
    override val message: String? =
        "Invalid action. Current phase is $currentPhase."
}