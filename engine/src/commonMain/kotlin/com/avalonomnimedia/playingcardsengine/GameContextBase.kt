package com.avalonomnimedia.playingcardsengine

abstract class GameContextBase {
    abstract val players: List<PlayerBase<out Hand>>
    abstract val cardComparator: Comparator<Card>
    abstract val deck: PlayingDeck
}