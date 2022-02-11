package com.avalonomnimedia.war.cli

import com.avalonomnimedia.playingcardsengine.Card
import com.avalonomnimedia.playingcardsengine.FaceDownStack
import com.avalonomnimedia.playingcardsengine.PlayingDeck
import com.avalonomnimedia.playingcardsengine.Suit
import com.avalonomnimedia.playingcardsengine.Value
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class TestGameContext {
    @Test
    fun `player1 should be first player`(){
        val uut = GameContext(listOf(WarPlayer(""), mockk<WarPlayer>()), mockk(), mockk())
        val expected = uut.players[0]

        val actual = uut.player1

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `player2 should be second player`(){
        val uut = GameContext(listOf(mockk<WarPlayer>(), WarPlayer("")), mockk(), mockk())
        val expected = uut.players[1]

        val actual = uut.player2

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `compareTo should call compare() on cardComparator`() {
        val comparator = mockk<CardComparator>()
        val card1 = Card(Suit.CLUBS, Value.ACE)
        val card2 = Card(Suit.CLUBS, Value.TEN)
        every { comparator.compare(card1, card2) }.returns(1)

        val uut = GameContext(listOf<WarPlayer>(mockk(), mockk()), comparator, mockk())

        uut.apply { card1.compareTo(card2) }

        verify { comparator.compare(card1, card2) }
    }

    @Test
    fun `when onDeal called, cards should be dealt alternately to each player`() {
        val expected = FourCardDeck().cards
        val deck = PlayingDeck().apply { add(FourCardDeck())}
        val uut = GameContext(listOf(WarPlayer(""), WarPlayer("")), mockk(), deck)

        uut.onDeal()

        val actual1 = uut.player1.hand?.takeTop()
        Assert.assertEquals(expected[0], actual1)
        val actual2 = uut.player2.hand?.takeTop()
        Assert.assertEquals(expected[1], actual2)
        val actual3 = uut.player1.hand?.takeTop()
        Assert.assertEquals(expected[2], actual3)
        val actual4 = uut.player2.hand?.takeTop()
        Assert.assertEquals(expected[3], actual4)
    }

    @Test
    fun `when onFlip is called and currentBattle is null, new Battle should be created`() {
        val hand = mockk<FaceDownStack>()
        val player = mockk<WarPlayer>()

        val uut = GameContext(listOf(player, player), mockk(), mockk())

        every { player.hand }.returns(hand)
        every { hand.takeTop() }.returns(mockk())

        uut.onFlip()
        val actual = uut.currentBattle

        Assert.assertNotNull(actual)
    }

    @Test
    fun `when onFlip is called and currentBattle is not null, currentBattle should be used`() {
        val hand = mockk<FaceDownStack>()
        val player = mockk<WarPlayer>()

        val uut = GameContext(listOf(player, player), mockk(), mockk())

        uut.currentBattle = mockk()

        every { uut.currentBattle!!.player1Stack.discard(any()) }.just(runs)
        every { uut.currentBattle!!.player2Stack.discard(any()) }.just(runs)
        every { player.hand }.returns(hand)
        every { hand.takeTop() }.returns(mockk())

        uut.onFlip()

        verify { uut.currentBattle!!.player1Stack.discard(any()) }
        verify { uut.currentBattle!!.player2Stack.discard(any()) }
    }

    @Test
    fun `when onFlip is called, player1's top card should be placed in their stack on currentBattle`() {
        val expected = Card(Suit.HEARTS, Value.ACE)
        val hand = FaceDownStack(listOf(expected))
        val player = WarPlayer("").apply { this.hand = hand }
        val uut = GameContext(listOf(player, WarPlayer("").apply { this.hand = FaceDownStack(listOf(Card(Suit.CLUBS, Value.ACE))) }), mockk(), mockk())

        uut.onFlip()
        val actual = uut.currentBattle?.player1Stack?.peekTop()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when onFlip is called, player2's top card should be placed in their stack on currentBattle`() {
        val expected = Card(Suit.HEARTS, Value.ACE)
        val hand = FaceDownStack(listOf(expected))
        val player = WarPlayer("").apply { this.hand = hand }
        val uut = GameContext(listOf(WarPlayer("").apply { this.hand = FaceDownStack(listOf(Card(Suit.CLUBS, Value.ACE))) }, player), mockk(), mockk())

        uut.onFlip()
        val actual = uut.currentBattle?.player2Stack?.peekTop()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when onCompareCards is called and player1 has greater card, should return true`() {
        val cardComparator = mockk<CardComparator>()
        val card = mockk<Card>()
        val uut = GameContext(listOf<WarPlayer>(mockk(), mockk()), cardComparator, mockk())
        uut.currentBattle = Battle().apply { player1Stack.discard(card); player2Stack.discard(card) }

        every { cardComparator.compare(card, card) }.returns(1)

        val actual = uut.onCompareCards()

        Assert.assertTrue(actual)
    }

    @Test
    fun `when onCompareCards is called and player2 has greater card, should return true`() {
        val cardComparator = mockk<CardComparator>()
        val card = mockk<Card>()
        val uut = GameContext(listOf<WarPlayer>(mockk(), mockk()), cardComparator, mockk())
        uut.currentBattle = Battle().apply { player1Stack.discard(card); player2Stack.discard(card) }

        every { cardComparator.compare(card, card) }.returns(-1)

        val actual = uut.onCompareCards()

        Assert.assertTrue(actual)
    }

    @Test
    fun `when onCompareCards is called and players have same card, should return false`() {
        val cardComparator = mockk<CardComparator>()
        val card = mockk<Card>()
        val uut = GameContext(listOf<WarPlayer>(mockk(), mockk()), cardComparator, mockk())
        uut.currentBattle = Battle().apply { player1Stack.discard(card); player2Stack.discard(card) }

        every { cardComparator.compare(card, card) }.returns(0)

        val actual = uut.onCompareCards()

        Assert.assertFalse(actual)
    }

    @Test(expected = MissingBattleException::class)
        fun `when onWinnerGetsCards is called and currentBattle is null, should throw MissingBattleException`() {
        val uut = GameContext(listOf<WarPlayer>(mockk(), mockk()), mockk(), mockk())

        uut.onWinnerGetsCards()
    }

    @Test
    fun `when onWinnerGetsCards is called and winner is not null, all cards should go to winner`() {
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

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when onWinnerGetsCards is called, currentBattle should be set to null`() {
        val uut = GameContext(listOf<WarPlayer>(mockk(), mockk()), mockk(), mockk())

        val battle = mockk<Battle>()
        every { battle.takeAllCards() }.returns(emptyList())

        uut.currentBattle = battle
        uut.onWinnerGetsCards()
        val actual = uut.currentBattle

        Assert.assertNull(actual)
    }

    @Test
    fun `when onCountStacks called and both players' hands are null, should return false`() {
        val player = mockk<WarPlayer>()
        every { player.hand }.returns(null)

        val uut = GameContext(listOf(player, player), mockk(), mockk())

        val actual = uut.onCountStacks()

        Assert.assertFalse(actual)
    }

    @Test
    fun `when onCountStacks called and both players' hand counts are less than 52, should return false`() {
        val player = mockk<WarPlayer>()
        val hand = mockk<FaceDownStack>()
        every { player.hand }.returns(hand)
        every { hand.count() }.returns(51)

        val uut = GameContext(listOf(player, player), mockk(), mockk())

        val actual = uut.onCountStacks()

        Assert.assertFalse(actual)
    }

    @Test
    fun `when onCountStacks called and player1 hand count is 52, should return true`() {
        val player = mockk<WarPlayer>()
        val hand = mockk<FaceDownStack>()
        every { player.hand }.returns(hand)
        every { hand.count() }.returns(52)

        val uut = GameContext(listOf(player, mockk<WarPlayer>()), mockk(), mockk())

        val actual = uut.onCountStacks()

        Assert.assertTrue(actual)
    }

    @Test
    fun `when onCountStacks called and player2 hand count is 52, should return true`() {
        val player1 = mockk<WarPlayer>()
        val player2 = mockk<WarPlayer>()
        val hand = mockk<FaceDownStack>()
        every { player1.hand }.returns(null)
        every { player2.hand }.returns(hand)
        every { hand.count() }.returns(52)

        val uut = GameContext(listOf(player1, player2), mockk(), mockk())

        val actual = uut.onCountStacks()

        Assert.assertTrue(actual)
    }
}