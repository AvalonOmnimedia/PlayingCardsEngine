package com.avalonomnimedia.war.cli

import com.avalonomnimedia.playingcardsengine.PlayingDeck
import com.avalonomnimedia.playingcardsengine.Shuffler
import com.avalonomnimedia.playingcardsengine.StandardDeck
import com.avalonomnimedia.playingcardsengine.createRunner
import com.avalonomnimedia.war.core.AnteUp
import com.avalonomnimedia.war.core.BattleStarted
import com.avalonomnimedia.war.core.BattleWon
import com.avalonomnimedia.war.core.CardComparator
import com.avalonomnimedia.war.core.CompareCards
import com.avalonomnimedia.war.core.CountStacks
import com.avalonomnimedia.war.core.Deal
import com.avalonomnimedia.war.core.Flip
import com.avalonomnimedia.war.core.GameContext
import com.avalonomnimedia.war.core.GameOver
import com.avalonomnimedia.war.core.MissingBattleException
import com.avalonomnimedia.war.core.ReadyToFlip
import com.avalonomnimedia.war.core.RoundOver
import com.avalonomnimedia.war.core.Start
import com.avalonomnimedia.war.core.Tie
import com.avalonomnimedia.war.core.WarPlayer
import com.avalonomnimedia.war.core.WinnerGetsCards
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.output.TermUi

fun main(args: Array<String>) = App().main(args)

class App : CliktCommand() {
    private var numberOfRounds = 1
    private val gameContext = GameContext(
        listOf(WarPlayer("Player 1"), WarPlayer("Player 2")),
        CardComparator(),
        PlayingDeck(Shuffler()).apply { add(StandardDeck()); shuffle() }
    )

    private val runner = createRunner(gameContext, Start()) {
        phase<Start> {
            onEntry { TermUi.echo("Let's play!") }
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

                TermUi.echo("Round $numberOfRounds")
                TermUi.echo("Player 1: $card1")
                TermUi.echo("Player 2: $card2")
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
            onEntry { TermUi.echo("Tie! It's war!") }
            on<AnteUp> {
                transitionTo<ReadyToFlip>()
            }
        }
        phase<BattleWon> {
            onEntry {
                val battle = gameContext.currentBattle ?: throw MissingBattleException()
                TermUi.echo("${battle.winner} won!")
            }
            on<WinnerGetsCards> {
                onWinnerGetsCards()
                transitionTo<RoundOver>()
            }
        }
        phase<RoundOver> {
            onEntry {
                TermUi.echo("Current score: ${gameContext.player1.hand?.count()} to ${gameContext.player2.hand?.count()}")
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
            onEntry { TermUi.echo("Game over! Player${if (gameContext.player1.hand?.count() == 52) "1" else "2"}") }
        }
    }

    override fun run() {
        while (true) {
            val action = when (runner.currentPhase) {
                is Start -> Deal
                is ReadyToFlip -> Flip
                is BattleStarted -> CompareCards
                is Tie -> AnteUp
                is BattleWon -> WinnerGetsCards
                is RoundOver -> CountStacks
                is GameOver -> return
                else -> throw Exception()
            }
            runner.perform(action)
        }
    }
}