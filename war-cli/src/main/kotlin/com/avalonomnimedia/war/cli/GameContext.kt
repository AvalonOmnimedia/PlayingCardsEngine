package com.avalonomnimedia.war.cli

import com.avalonomnimedia.playingcardsengine.Card
import com.avalonomnimedia.playingcardsengine.DiscardPile
import com.avalonomnimedia.playingcardsengine.FaceDownStack
import com.avalonomnimedia.playingcardsengine.GameContextBase
import com.avalonomnimedia.playingcardsengine.Hand
import com.avalonomnimedia.playingcardsengine.PlayerBase
import com.avalonomnimedia.playingcardsengine.PlayingDeck

class GameContext(
    override val players: List<PlayerBase<out Hand>>,
    override val cardComparator: Comparator<Card>,
    override val deck: PlayingDeck
) : GameContextBase() {
    operator fun Card.compareTo(otherCard: Card): Int {
        return cardComparator.compare(this, otherCard)
    }

    val player1 = players[0] as WarPlayer
    val player2 = players[1] as WarPlayer

    var currentBattle: Battle? = null

    fun onDeal() {
        val listsOfCards = listOf<MutableList<Card>>(mutableListOf(), mutableListOf())

        while (deck.count() > 0) {
            listsOfCards[deck.count().rem(2)].add(deck.takeCard())
        }

        player1.hand = FaceDownStack(listsOfCards[0])
        player2.hand = FaceDownStack(listsOfCards[1])
    }

    fun onFlip() {
        val battle = Battle()
        battle.player1Stack.discard(player1.hand!!.takeTop())
        battle.player2Stack.discard(player2.hand!!.takeTop())

        currentBattle = battle
    }

    fun onCompareCards(): HasWinner {
        val battle = currentBattle ?: throw IllegalStateException()
        val card1 = battle.player1Stack.peekTop()
        val card2 = battle.player2Stack.peekTop()

        return when {
            card1 > card2 -> {
                battle.winner = player1
                true
            }
            card1 < card2 -> {
                battle.winner = player2
                true
            }
            else -> false
        }
    }

    fun onWinnerGetsCards() {
        val battle = currentBattle ?: throw MissingBattleException()
        battle.takeAllCards().forEach {
            battle.winner?.hand?.addToBottom(it)
        }
        currentBattle = null
    }

    fun onCountStacks(): IsGameOver {
        return player1.hand?.count() == 52 || player2.hand?.count() == 52
    }
}

class MissingBattleException : Exception()

typealias HasWinner = Boolean
typealias IsGameOver = Boolean

class CardComparator : Comparator<Card> {
    override fun compare(card1: Card, card2: Card): Int {
        return when {
            card1.value > card2.value -> 1
            card1.value < card2.value -> -1
            else -> 0
        }
    }
}

class WarPlayer(name: String) : PlayerBase<FaceDownStack>(name)

class Battle {
    var winner: WarPlayer? = null

    val player1Stack = DiscardPile()
    val player2Stack = DiscardPile()

    fun takeAllCards(): List<Card> {

        val list = mutableListOf<Card>()
        while(player1Stack.isNotEmpty) {
            list.add(player1Stack.takeTop())
        }

        while(player2Stack.isNotEmpty) {
            list.add(player2Stack.takeTop())
        }

        return list
    }
}

