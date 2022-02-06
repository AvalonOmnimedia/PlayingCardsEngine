package com.avalonomnimedia.mayi

import com.avalonomnimedia.playingcardsengine.IGameAction

sealed class GameAction : IGameAction
object Deal : GameAction()