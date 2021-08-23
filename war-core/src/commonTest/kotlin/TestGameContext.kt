import com.avalonomnimedia.playingcardsengine.Card
import com.avalonomnimedia.playingcardsengine.FaceDownStack
import com.avalonomnimedia.playingcardsengine.PlayingDeck
import com.avalonomnimedia.playingcardsengine.Shuffler
import com.avalonomnimedia.playingcardsengine.Suit
import com.avalonomnimedia.playingcardsengine.Value
import com.avalonomnimedia.war.core.Battle
import com.avalonomnimedia.war.core.CardComparator
import com.avalonomnimedia.war.core.GameContext
import com.avalonomnimedia.war.core.MissingBattleException
import com.avalonomnimedia.war.core.WarPlayer
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TestGameContext {
    @Test
    fun player1ShouldBeFirstPlayer(){
        val uut = GameContext(listOf(WarPlayer(""), mockk()), mockk(), mockk())
        val expected = uut.players[0]

        val actual = uut.player1

        assertEquals(expected, actual)
    }

    @Test
    fun player2ShouldBeSecondPlayer(){
        val uut = GameContext(listOf(mockk(), WarPlayer("")), mockk(), mockk())
        val expected = uut.players[1]

        val actual = uut.player2

        assertEquals(expected, actual)
    }

    @Test
    fun compareToShouldCallCompareOnCardComparator() {
        val comparator = mockk<CardComparator>()
        val card1 = Card(Suit.CLUBS, Value.ACE)
        val card2 = Card(Suit.CLUBS, Value.TEN)
        every { comparator.compare(card1, card2) }.returns(1)

        val uut = GameContext(listOf<WarPlayer>(mockk(), mockk()), comparator, mockk())

        uut.apply { card1.compareTo(card2) }

        verify { comparator.compare(card1, card2) }
    }

    @Test
    fun whenOnDealCalled_shouldDealCardsAlternatelyToEachPlayer() {
        val shuffler = mockk<Shuffler>()

        val expected = FourCardDeck().cards
        val deck = PlayingDeck(shuffler).apply { add(FourCardDeck())}
        val uut = GameContext(listOf(WarPlayer(""), WarPlayer("")), mockk(), deck)

        uut.onDeal()

        val actual1 = uut.player1.hand?.takeTop()
        assertEquals(expected[0], actual1)
        val actual2 = uut.player2.hand?.takeTop()
        assertEquals(expected[1], actual2)
        val actual3 = uut.player1.hand?.takeTop()
        assertEquals(expected[2], actual3)
        val actual4 = uut.player2.hand?.takeTop()
        assertEquals(expected[3], actual4)
    }

    @Test
    fun whenOnFlipIsCalled_shouldCurrentBattleNotBeNull() {
        val hand = mockk<FaceDownStack>()
        val player = mockk<WarPlayer>()

        val uut = GameContext(listOf(player, player), mockk(), mockk())

        every { player.hand }.returns(hand)
        every { hand.takeTop() }.returns(mockk())

        uut.onFlip()
        val actual = uut.currentBattle

        assertNotNull(actual)
    }

    @Test
    fun whenOnFlipIsCalled_shouldPlaceTopCardOfPlayer1InTheirStackOnCurrentBattle() {
        val expected = Card(Suit.HEARTS, Value.ACE)
        val hand = FaceDownStack(listOf(expected))
        val player = WarPlayer("").apply { this.hand = hand }
        val uut = GameContext(listOf(player, WarPlayer("").apply { this.hand = FaceDownStack(listOf(Card(Suit.CLUBS, Value.ACE))) }), mockk(), mockk())

        uut.onFlip()
        val actual = uut.currentBattle?.player1Stack?.peekTop()

        assertEquals(expected, actual)
    }

    @Test
    fun whenOnFlipIsCalled_shouldPlaceTopCardOfPlayer2InTheirStackOnCurrentBattle() {
        val expected = Card(Suit.HEARTS, Value.ACE)
        val hand = FaceDownStack(listOf(expected))
        val player = WarPlayer("").apply { this.hand = hand }
        val uut = GameContext(listOf(WarPlayer("").apply { this.hand = FaceDownStack(listOf(Card(Suit.CLUBS, Value.ACE))) }, player), mockk(), mockk())

        uut.onFlip()
        val actual = uut.currentBattle?.player2Stack?.peekTop()

        assertEquals(expected, actual)
    }

    @Test
    fun whenOnCompareCardsIsCalledAndPlayer1HasGreaterCard_shouldReturnTrue() {
        val cardComparator = mockk<CardComparator>()
        val card = mockk<Card>()
        val uut = GameContext(listOf<WarPlayer>(mockk(), mockk()), cardComparator, mockk())
        uut.currentBattle = Battle().apply { player1Stack.discard(card); player2Stack.discard(card) }

        every { cardComparator.compare(card, card) }.returns(1)

        val actual = uut.onCompareCards()

        assertTrue(actual)
    }

    @Test
    fun whenOnCompareCardsIsCalledAndPlayer2HasGreaterCard_shouldReturnTrue() {
        val cardComparator = mockk<CardComparator>()
        val card = mockk<Card>()
        val uut = GameContext(listOf<WarPlayer>(mockk(), mockk()), cardComparator, mockk())
        uut.currentBattle = Battle().apply { player1Stack.discard(card); player2Stack.discard(card) }

        every { cardComparator.compare(card, card) }.returns(-1)

        val actual = uut.onCompareCards()

        assertTrue(actual)
    }

    @Test
    fun whenOnCompareCardsIsCalledAndPlayersHaveSameCard_shouldReturnFalse() {
        val cardComparator = mockk<CardComparator>()
        val card = mockk<Card>()
        val uut = GameContext(listOf<WarPlayer>(mockk(), mockk()), cardComparator, mockk())
        uut.currentBattle = Battle().apply { player1Stack.discard(card); player2Stack.discard(card) }

        every { cardComparator.compare(card, card) }.returns(0)

        val actual = uut.onCompareCards()

        assertFalse(actual)
    }

    @Test
    fun whenOnWinnerGetsCardsIsCalledAndCurrentBattleIsNull_shouldThrowMissingBattleException() {
        val uut = GameContext(listOf<WarPlayer>(mockk(), mockk()), mockk(), mockk())

        assertFailsWith(MissingBattleException::class) {
            uut.onWinnerGetsCards()
        }
    }

    @Test
    fun whenOnWinnerGetsCardsIsCalledAndWinnerIsNotNull_shouldGiveAllCardsToWinner() {
        val uut = GameContext(listOf<WarPlayer>(mockk(), mockk()), mockk(), mockk())
        val winner = mockk<WarPlayer>()
        val hand = FaceDownStack()
        every { winner.hand }.returns(hand)

        val expected = FourCardDeck().cards

        val battle = mockk<Battle>()
        every { battle.winner }.returns(winner)
        every { battle.takeAllCards() }.returns(expected)

        uut.currentBattle = battle
        uut.onWinnerGetsCards()
        val actual = mutableListOf<Card>().apply { while(hand.count() > 0) { add(hand.takeTop()) } }

        assertEquals(expected, actual)
    }

    @Test
    fun whenOnWinnerGetsCardsIsCalled_shouldSetCurrentBattleToNull() {
        val uut = GameContext(listOf<WarPlayer>(mockk(), mockk()), mockk(), mockk())

        val battle = mockk<Battle>()
        every { battle.takeAllCards() }.returns(emptyList())

        uut.currentBattle = battle
        uut.onWinnerGetsCards()
        val actual = uut.currentBattle

        assertNull(actual)
    }

    @Test
    fun whenOnCountStacksCalledAndBothPlayersHandsAreNull_shouldReturnFalse() {
        val player = mockk<WarPlayer>()
        every { player.hand }.returns(null)

        val uut = GameContext(listOf(player, player), mockk(), mockk())

        val actual = uut.onCountStacks()

        assertFalse(actual)
    }

    @Test
    fun whenOnCountStacksCalledAndBothPlayersHandCountsAreLessThan52_shouldReturnFalse() {
        val player = mockk<WarPlayer>()
        val hand = mockk<FaceDownStack>()
        every { player.hand }.returns(hand)
        every { hand.count() }.returns(51)

        val uut = GameContext(listOf(player, player), mockk(), mockk())

        val actual = uut.onCountStacks()

        assertFalse(actual)
    }

    @Test
    fun whenOnCountStacksCalledAndPlayer1HandCountIs52_shouldReturnTrue() {
        val player = mockk<WarPlayer>()
        val hand = mockk<FaceDownStack>()
        every { player.hand }.returns(hand)
        every { hand.count() }.returns(52)

        val uut = GameContext(listOf(player, mockk()), mockk(), mockk())

        val actual = uut.onCountStacks()

        assertTrue(actual)
    }

    @Test
    fun whenOnCountStacksCalledAndPlayer2HandCountIs52_shouldReturnTrue() {
        val player1 = mockk<WarPlayer>()
        val player2 = mockk<WarPlayer>()
        val hand = mockk<FaceDownStack>()
        every { player1.hand }.returns(null)
        every { player2.hand }.returns(hand)
        every { hand.count() }.returns(52)

        val uut = GameContext(listOf(player1, player2), mockk(), mockk())

        val actual = uut.onCountStacks()

        assertTrue(actual)
    }
}