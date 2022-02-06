package com.avalonomnimedia.mayi

import com.avalonomnimedia.playingcardsengine.IGamePhase
import com.avalonomnimedia.playingcardsengine.IGameEnd
import com.avalonomnimedia.playingcardsengine.IGameStart

sealed class GamePhase : IGamePhase
class Start : GamePhase(), IGameStart
class GameOver : GamePhase(), IGameEnd