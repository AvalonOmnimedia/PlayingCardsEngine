package com.avalonomnimedia.mayi

import com.avalonomnimedia.playingcardsengine.IGamePhase
import com.avalonomnimedia.playingcardsengine.IGameEnd
import com.avalonomnimedia.playingcardsengine.IGameStart
import com.avalonomnimedia.playingcardsengine.IRoundStart

sealed class GamePhase : IGamePhase
class Start : GamePhase(), IGameStart
class RoundStart : GamePhase(), IRoundStart
class GameOver : GamePhase(), IGameEnd