package com.avalonomnimedia.mayi

import com.avalonomnimedia.playingcardsengine.PlayingDeck
import com.avalonomnimedia.playingcardsengine.StandardDeck
import com.avalonomnimedia.playingcardsengine.createRunner
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.output.TermUi
import java.util.LinkedList

fun main(args: Array<String>) = App().main(args)

class App : CliktCommand() {
    private val gameContext = GameContext(
        listOf(MayIPlayer("Player 1"), MayIPlayer("Player 2"), MayIPlayer("Player 3"), MayIPlayer("Player 4")),
        CardComparator(),
        PlayingDeck().apply { add(StandardDeck()); add(StandardDeck()); shuffle() },
        LinkedList(listOf(Round(1, 10)))
    )

    private val runner = createRunner(gameContext, Start()) {
        phase<Start> {
            onEntry {
                TermUi.echo("Let's play!")
            }
            on<StartGame> {
                transitionTo<RoundStart>()
            }
        }
        phase<RoundStart> {
            onEntry {
                TermUi.echo("Round ${gameContext.currentRound.number}! Each player will be dealt ${gameContext.currentRound.dealAmount} cards.")
            }
            on<Deal> {
                onDeal()
                transitionTo<GameOver>()
            }
        }
        phase<GameOver> {
            onEntry { TermUi.echo("Game over!") }
        }
    }

    override fun run() {
        while (true) {
            runner.perform(
                when (runner.currentPhase) {
                    is Start -> StartGame
                    is RoundStart -> Deal
                    is GameOver -> return
                    else -> throw Exception()
                }
            )
        }
    }
}