# PlayingCardsEngine

[![Build Status](https://avalon.visualstudio.com/Playing%20Cards%20Engine/_apis/build/status/AvalonOmnimedia.PlayingCardsEngine)](https://avalon.visualstudio.com/Playing%20Cards%20Engine/_build/latest?definitionId=14)

This is a simple library to give you a jump start on writing your own card games!

In this repo, there are two pieces:

[The engine](https://github.com/AvalonOmnimedia/PlayingCardsEngine/tree/master/engine)

[An example game](https://github.com/AvalonOmnimedia/PlayingCardsEngine/tree/master/war-cli)

## The engine

The engine is really a collection of components. You are not required to use all the components (particularly the GameRunner) if you do not want to.

#### Basic

- [Deck](#deck)
- [Hand](#hand)
- [Player](#player)


#### Advanced

- [Phases](#phases)
- [Actions](#actions)
- [GameContext](#gamecontext)
- [GameRunner](#gamerunner)

## Basic Components

### Deck

The most fundamental building block of a card game is the [Deck](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/master/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/Deck.kt#L9).  This is an abstract class with the most basic functionality.  What is actually used for game play is the [PlayingDeck](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/master/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/Deck.kt#L22).  This is what players will draw from and possibly discard to (there is also a [DiscardPile](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/75d1e3d1b3036e0fdd771fafa5e1ed8fc57cc7b4/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/Deck.kt#L42) if that is necessary).  The `PlayingDeck`  starts empty and must have other decks added to it.  For example:

- Draw poker uses one [french deck](https://en.wikipedia.org/wiki/French_playing_cards).
- Casino Blackjack uses multiple french decks.
- Euchre uses one [stripped deck](https://en.wikipedia.org/wiki/Stripped_deck)

A 52 card [StandardDeck](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/master/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/Deck.kt#L67) is included in the library, but you are free to make your own as well.

### Hand

Like the `Deck`, the [Hand](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/master/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/Hand.kt#L6) is an abstract class with just the base functionality.  Two flavors of hands are also included in the library: the [FaceUpHand](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/master/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/Hand.kt#L19) and the [FaceDownStack](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/master/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/Hand.kt#L31).

### Player

Yet another abstract class, the [PlayerBase](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/master/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/PlayerBase.kt#L8) is slightly different because it is generic so that the correct implementation of `Hand` can be referenced.

## Advanced Components

### Phases

A **phase** is really just a class that implements the [IGamePhase](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/master/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/GameRunner.kt#L58) interface. It doesn't have any properties or methods on it; it just signals where you are in game play. If you like, you can create a sealed class that all your phases can then inherit from (gaining all the [magic powers](https://kotlinlang.org/docs/reference/sealed-classes.html) that Kotlin bestows on them) but it's not necessary either. Here are the phases in the example project:

```kotlin
sealed class GamePhase : IGamePhase
class Start : GamePhase(), IGameStart
class ReadyToFlip : GamePhase(), IRoundStart
class BattleStarted : GamePhase()
class RoundOver : GamePhase(), IRoundEnd
class GameOver : GamePhase(), IGameEnd

sealed class BattleOutcome : GamePhase()
class BattleWon : BattleOutcome()
class Tie : BattleOutcome()
```

> Note: If using the `GameRunner`, phases **must** be a class, not an object.  They are created through reflection, which does not work with objects.

Your phases can also be marked to distinguish between the start of a game, start of a round, etc. This is there for your use and is currently not used by the engine (but it may be in the future).

### Actions

**Actions** implement the [IGameAction](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/master/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/GameRunner.kt#L59) interface and are how you move from one phase to the next.  Here, again, is how they are setup in the example project:

```kotlin
sealed class GameAction : IGameAction
object Deal : GameAction()
object Flip : GameAction()
object CompareCards : GameAction()
object WinnerGetsCards : GameAction()
object AnteUp : GameAction()
object CountStacks : GameAction()
```

### GameContext

The [GameContextBase](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/master/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/GameContextBase.kt#L3) is an abstract class that will help you bring your various components along from phase to phase. It is the real state of your game. The one property that is not from the library is the `cardComparator`.  Since every game has a distinct way of comparing the value of cards (i.e. are aces high or low, do suits matter, etc.), you must build this yourself.  Here is the `cardComparator` from the example game:

```kotlin
class CardComparator : Comparator<Card> {
    override fun compare(card1: Card, card2: Card): Int {
        return when {
            card1.value > card2.value -> 1
            card1.value < card2.value -> -1
            else -> 0
        }
    }
}
```

### GameRunner

Finally, the [GameRunner](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/master/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/GameRunner.kt#L14). This is your state machine. Use the [createRunner(...)](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/master/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/GameRunner.kt#L8) method to build it, along with all of your phases.  It is generic so the full power of your GameContextBase implementation can be utilized.

Each phase that is going to be used in your game must be registered upfront.  You can register the phase using the [phase\<IGamePhase\>](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/master/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/GameRunner.kt#L30) method.  Every registered phase can have three parts: [onEntry](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/75d1e3d1b3036e0fdd771fafa5e1ed8fc57cc7b4/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/PhaseDefinition.kt#L36) functions, [onExit](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/75d1e3d1b3036e0fdd771fafa5e1ed8fc57cc7b4/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/PhaseDefinition.kt#L51) functions, and [on\<IGameAction\>](https://github.com/AvalonOmnimedia/PlayingCardsEngine/blob/75d1e3d1b3036e0fdd771fafa5e1ed8fc57cc7b4/engine/src/main/kotlin/com/avalonomnimedia/playingcardsengine/PhaseDefinition.kt#L22) transitions (and it can have multiple of each).

Here is a simple example from the included game:

```kotlin
phase<Start> {
    onEntry { TermUi.echo("Let's play!") }
    on<Deal> {
        onDeal()
        transitionTo<ReadyToFlip>()
    }
}
```

In the on\<IGameAction\> lambda, the parameter is the `GameContext` on the runner (in the example above, `onDeal` is a method on `GameContext`). The lambda must also return a KClass<IGamePhase>, which the helper function `transitionTo<IGamePhase>` will do for you.

Once all your phases are registered, all you need to do it call `perform(IGameAction)` on the `GameRunner` and, if an action has being added for the current phase, the runner will fire every transition lambda and marshal you into the next phase.

---

And that's it! More is planned for the library, so please stay tuned!