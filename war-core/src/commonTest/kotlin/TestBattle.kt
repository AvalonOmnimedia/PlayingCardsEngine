import com.avalonomnimedia.playingcardsengine.Suit
import com.avalonomnimedia.war.core.Battle
import kotlin.test.Test
import kotlin.test.assertEquals

class TestBattle {
    @Test
    fun whenTakeAllCardsIsCalled_shouldReturnAllCardsFromPlayer1Stack() {
        val expected = FourCardDeck().cards

        val uut = Battle()

        for(card in FourCardDeck().cards.reversed()) uut.player1Stack.discard(card)

        val actual = uut.takeAllCards()

        assertEquals(expected, actual)
    }

    @Test
    fun whenTakeAllCardsIsCalled_shouldReturnAllCardsFromPlayer2Stack() {
        val expected = FourCardDeck().cards

        val uut = Battle()

        for(card in FourCardDeck().cards.reversed()) uut.player2Stack.discard(card)

        val actual = uut.takeAllCards()

        assertEquals(expected, actual)
    }

    @Test
    fun whenTakeAllCardsIsCalled_shouldReturnAllCardsFromBothPlayer1StackAndPlayer2Stack() {
        val expected = FourCardDeck(Suit.HEARTS).cards + FourCardDeck(Suit.CLUBS).cards

        val uut = Battle()

        for(card in FourCardDeck(Suit.HEARTS).cards.reversed()) uut.player1Stack.discard(card)
        for(card in FourCardDeck(Suit.CLUBS).cards.reversed()) uut.player2Stack.discard(card)

        val actual = uut.takeAllCards()

        assertEquals(expected, actual)
    }
}