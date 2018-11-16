package com.avalonomnimedia.war.cli

import com.avalonomnimedia.playingcardsengine.Card
import com.avalonomnimedia.playingcardsengine.FaceDownStack
import com.avalonomnimedia.playingcardsengine.GameBase
import com.avalonomnimedia.playingcardsengine.GamePhaseBase
import com.avalonomnimedia.playingcardsengine.Hand
import com.avalonomnimedia.playingcardsengine.PlayerBase
import com.avalonomnimedia.playingcardsengine.PlayingDeck
import com.avalonomnimedia.playingcardsengine.StandardDeck

class Game(private val printer: (String) -> Unit) : GameBase() {
    override val players: List<PlayerBase<out Hand>> = listOf(WarPlayer("PlayerBase 1"), WarPlayer("PlayerBase 2"))
    override val cardComparator: Comparator<Card> = CardComparator()
    override val deck = PlayingDeck().apply { add(StandardDeck()); shuffle() }
    override var currentPhase: GamePhaseBase = Start()

    private val player1 = players[0] as WarPlayer
    private val player2 = players[1] as WarPlayer

    private val cardsOnTheTable = mutableListOf<Card>()

    override fun play() {
        var numberOfRounds = 1
        while (true) {
            currentPhase = when (val phase = currentPhase) {
                is Start -> {
                    printer("Let's play!")
                    phase.perform(Deal(deck))
                }
                is ReadyToFlip -> {
                    println("Round $numberOfRounds")
                    //prompt("Press enter to show card", "play")
                    player1.hand = phase.stack1
                    player2.hand = phase.stack2
                    phase.perform(Flip())
                }
                is Battle -> {
                    printer("PlayerBase 1: ${phase.card1}")
                    printer("PlayerBase 2: ${phase.card2}")
                    cardsOnTheTable.add(phase.card1)
                    cardsOnTheTable.add(phase.card2)
                    phase.perform(CompareCards(cardComparator, player1, player2))
                }
                is Tie -> {
                    printer("Tie! It's war!")
                    phase.perform(AnteUp(cardsOnTheTable, player1.hand!!, player2.hand!!))
                }
                is BattleWon -> {
                    printer("${phase.player} won!")
                    phase.perform(WinnerGetsCards(cardsOnTheTable)).also { cardsOnTheTable.clear() }
                }
                is RoundOver -> {
                    printer("Current score: ${player1.hand?.count()} to ${player2.hand?.count()}")
                    numberOfRounds++
                    phase.perform(CountStacks(player1.hand!!, player2.hand!!))
                }
                is GameOver -> {
                    printer("GameBase over! PlayerBase${if (players[0].hand?.count() == 52) "1" else "2"}")
                    return
                }
                else -> {
                    throw Exception()
                }
            }
        }
    }
}

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