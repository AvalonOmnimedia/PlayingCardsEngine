package com.avalonomnimedia.war.cli

import com.avalonomnimedia.playingcardsengine.PlayingDeck
import com.avalonomnimedia.playingcardsengine.StandardDeck
import com.avalonomnimedia.playingcardsengine.createRunner
import com.github.ajalt.clikt.core.CliktCommand

fun main(args: Array<String>) = App().main(args)

class App : CliktCommand() {
    private var numberOfRounds = 1
    private val gameContext = GameContext(
        listOf(WarPlayer("Player 1"), WarPlayer("Player 2")),
        CardComparator(),
        PlayingDeck().apply { add(StandardDeck()); shuffle() }
    )

    private val runner = createRunner(gameContext, Start()) {
        phase<Start> {
            onEntry { echo("Let's play!") }
            on<Deal> {
                onDeal()
                transitionTo<ReadyToFlip>()
            }
        }
        phase<ReadyToFlip> {
            onEntry { println("Round $numberOfRounds") }
            on<Flip> {
                onFlip()
                transitionTo<BattleStarted>()
            }
        }
        phase<BattleStarted> {
            onEntry {
                val battle = gameContext.currentBattle ?: throw IllegalStateException()
                val card1 = battle.player1Stack.peekTop()
                val card2 = battle.player2Stack.peekTop()

                echo("Round $numberOfRounds")
                echo("Player 1: $card1")
                echo("Player 2: $card2")
            }
            on<CompareCards> {
                val hasWinner = onCompareCards()
                if (hasWinner) {
                    transitionTo<BattleWon>()
                } else {
                    transitionTo<Tie>()
                }
            }
        }
        phase<Tie> {
            onEntry { echo("Tie! It's war!") }
            on<AnteUp> {
                transitionTo<ReadyToFlip>()
            }
        }
        phase<BattleWon> {
            onEntry {
                val battle = gameContext.currentBattle ?: throw IllegalStateException()
                echo("${battle.winner} won!")
            }
            on<WinnerGetsCards> {
                onWinnerGetsCards()
                transitionTo<RoundOver>()
            }
        }
        phase<RoundOver> {
            onEntry {
                echo("Current score: ${gameContext.player1.hand?.count()} to ${gameContext.player2.hand?.count()}")
                numberOfRounds++
            }
            on<CountStacks> {
                val isGameOver = onCountStacks()
                if (isGameOver) {
                    transitionTo<GameOver>()
                } else {
                    transitionTo<ReadyToFlip>()
                }
            }
        }
        phase<GameOver> {
            onEntry { echo("Game over! Player${if (gameContext.player1.hand?.count() == 52) "1" else "2"}") }
        }
    }

    override fun run() {
        while (true) {
            runner.perform(
                when (runner.currentPhase) {
                    is Start -> Deal
                    is ReadyToFlip -> Flip
                    is BattleStarted -> CompareCards
                    is Tie -> AnteUp
                    is BattleWon -> WinnerGetsCards
                    is RoundOver -> CountStacks
                    is GameOver -> return
                    else -> throw Exception()
                }
            )
        }
    }
}