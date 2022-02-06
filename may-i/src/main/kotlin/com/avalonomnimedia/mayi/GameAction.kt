package com.avalonomnimedia.mayi

import com.avalonomnimedia.playingcardsengine.IGameAction

sealed class GameAction : IGameAction
object StartGame : GameAction()
object Deal : GameAction()