package com.avalonomnimedia.war.core

import com.avalonomnimedia.playingcardsengine.IGameAction

sealed class GameAction : IGameAction
object Deal : GameAction()
object Flip : GameAction()
object CompareCards : GameAction()
object WinnerGetsCards : GameAction()
object AnteUp : GameAction()
object CountStacks : GameAction()
