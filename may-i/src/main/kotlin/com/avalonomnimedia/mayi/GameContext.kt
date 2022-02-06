package com.avalonomnimedia.mayi

import com.avalonomnimedia.playingcardsengine.Card
import com.avalonomnimedia.playingcardsengine.FaceUpHand
import com.avalonomnimedia.playingcardsengine.GameContextBase
import com.avalonomnimedia.playingcardsengine.Hand
import com.avalonomnimedia.playingcardsengine.PlayerBase
import com.avalonomnimedia.playingcardsengine.PlayingDeck
import java.util.Queue
import kotlin.Comparator

class GameContext(
    override val players: List<PlayerBase<out Hand>>,
    override val cardComparator: Comparator<Card>,
    override val deck: PlayingDeck,
    rounds: Queue<Round>
) : GameContextBase() {
    operator fun Card.compareTo(otherCard: Card): Int {
        return cardComparator.compare(this, otherCard)
    }
    private val mayIPlayers = players as List<MayIPlayer>

    var currentRound = rounds.poll() ?: throw MissingRoundException()

    fun onDeal() {
        for (i in 1..currentRound.dealAmount) {
            for (player in mayIPlayers) {
                player.hand?.add(deck.takeCard())
            }
        }
    }
}

class MissingRoundException: Exception("No rounds defined!")

class CardComparator : Comparator<Card> {
    override fun compare(card1: Card, card2: Card): Int {
        return when {
            card1.value > card2.value -> 1
            card1.value < card2.value -> -1
            else -> 0
        }
    }
}

class MayIPlayer(name: String) : PlayerBase<FaceUpHand>(name) {
    init {
        hand = FaceUpHand()
    }
}

class Round(val number: Int, val dealAmount: Int)