package com.avalonomnimedia.war.core

import com.avalonomnimedia.playingcardsengine.IGamePhase
import com.avalonomnimedia.playingcardsengine.IGameEnd
import com.avalonomnimedia.playingcardsengine.IGameStart
import com.avalonomnimedia.playingcardsengine.IRoundEnd
import com.avalonomnimedia.playingcardsengine.IRoundStart

sealed class GamePhase : IGamePhase
class Start : GamePhase(), IGameStart
class ReadyToFlip : GamePhase(), IRoundStart
class BattleStarted : GamePhase()
class RoundOver : GamePhase(), IRoundEnd
class GameOver : GamePhase(), IGameEnd

sealed class BattleOutcome : GamePhase()
class BattleWon : BattleOutcome()
class Tie : BattleOutcome()