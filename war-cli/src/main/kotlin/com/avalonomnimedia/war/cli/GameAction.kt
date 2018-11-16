package com.avalonomnimedia.war.cli

import com.avalonomnimedia.playingcardsengine.Card
import com.avalonomnimedia.playingcardsengine.FaceDownStack
import com.avalonomnimedia.playingcardsengine.GameActionBase
import com.avalonomnimedia.playingcardsengine.GamePhaseBase
import com.avalonomnimedia.playingcardsengine.PlayingDeck

sealed class GameAction : GameActionBase()

class Deal(private val deck: PlayingDeck) : GameAction() {
    fun execute(): GamePhaseBase {
        val listsOfCards = listOf<MutableList<Card>>(mutableListOf(), mutableListOf())

        while (deck.count() > 0) {
            listsOfCards[deck.count().rem(2)].add(deck.takeCard())
        }

        return ReadyToFlip(FaceDownStack(listsOfCards[0]), FaceDownStack(listsOfCards[1]))
    }
}

class Flip : GameAction() {
    fun execute(stack1: FaceDownStack, stack2: FaceDownStack) : Battle {
        return Battle(stack1.takeTop(), stack2.takeTop())
    }
}

class CompareCards(
    private val cardComparator: Comparator<Card>,
    private val player1: WarPlayer,
    private val player2: WarPlayer
) : GameAction() {
    fun execute(card1: Card, card2: Card): BattleOutcome {
        return when (cardComparator.compare(card1, card2)) {
            1 -> BattleWon(player1)
            -1 -> BattleWon(player2)
            else -> Tie()
        }
    }
}

class WinnerGetsCards(private val cards: List<Card>) : GameAction() {
    fun execute(player: WarPlayer): RoundOver {
        cards.forEach {
            player.hand?.addToBottom(it)
        }

        return RoundOver()
    }
}

class AnteUp(
    private val cards: MutableList<Card>,
    private val stack1: FaceDownStack,
    private val stack2: FaceDownStack
) : GameAction() {
    fun execute(): ReadyToFlip {
        cards.add(stack1.takeTop())
        cards.add(stack2.takeTop())
        return ReadyToFlip(stack1, stack2)
    }
}

class CountStacks(private val stack1: FaceDownStack, private val stack2: FaceDownStack) : GameAction() {
    fun execute() : GamePhaseBase {
        return if (stack1.count() == 52 || stack2.count() == 52) GameOver() else ReadyToFlip(stack1, stack2)
    }
}
