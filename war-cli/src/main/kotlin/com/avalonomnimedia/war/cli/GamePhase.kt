package com.avalonomnimedia.war.cli

import com.avalonomnimedia.playingcardsengine.Card
import com.avalonomnimedia.playingcardsengine.FaceDownStack
import com.avalonomnimedia.playingcardsengine.GameActionBase
import com.avalonomnimedia.playingcardsengine.GamePhaseBase
import com.avalonomnimedia.playingcardsengine.IGameEnd
import com.avalonomnimedia.playingcardsengine.IGameStart
import com.avalonomnimedia.playingcardsengine.IRoundEnd
import com.avalonomnimedia.playingcardsengine.IRoundStart
import com.avalonomnimedia.playingcardsengine.InvalidActionException

sealed class GamePhase : GamePhaseBase()

class Start() : GamePhase(), IGameStart {
    override fun perform(action: GameActionBase): GamePhaseBase {
        return when (action) {
            is Deal -> action.execute()
            else -> {
                throw InvalidActionException(this)
            }
        }
    }
}

class ReadyToFlip(val stack1: FaceDownStack, val stack2: FaceDownStack) : GamePhase(), IRoundStart {
    override fun perform(action: GameActionBase): GamePhaseBase {
        return when (action) {
            is Flip -> action.execute(stack1, stack2)
            else -> {
                throw InvalidActionException(this)
            }
        }
    }
}

class Battle(val card1: Card, val card2: Card) : GamePhase() {
    override fun perform(action: GameActionBase): GamePhaseBase {
        return when (action) {
            is CompareCards -> action.execute(card1, card2)
            else -> {
                throw InvalidActionException(this)
            }
        }
    }
}

sealed class BattleOutcome : GamePhase()

class BattleWon(val player: WarPlayer) : BattleOutcome() {
    override fun perform(action: GameActionBase): GamePhaseBase {
        return when (action) {
            is WinnerGetsCards -> action.execute(player)
            else -> {
                throw InvalidActionException(this)
            }
        }
    }
}

class Tie : BattleOutcome() {
    override fun perform(action: GameActionBase): GamePhaseBase {
        return when (action) {
            is AnteUp -> action.execute()
            else -> {
                throw InvalidActionException(this)
            }
        }
    }
}

class RoundOver : GamePhase(), IRoundEnd {
    override fun perform(action: GameActionBase): GamePhaseBase {
        return when (action) {
            is CountStacks -> action.execute()
            else -> {
                throw InvalidActionException(this)
            }
        }
    }
}

class GameOver : GamePhase(), IGameEnd {
    override fun perform(action: GameActionBase): GamePhaseBase {
        throw InvalidActionException(this)
    }
}