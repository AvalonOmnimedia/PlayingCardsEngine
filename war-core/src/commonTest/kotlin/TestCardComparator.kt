import com.avalonomnimedia.playingcardsengine.Card
import com.avalonomnimedia.playingcardsengine.Suit
import com.avalonomnimedia.playingcardsengine.Value
import com.avalonomnimedia.war.core.CardComparator
import kotlin.test.Test
import kotlin.test.assertEquals

class TestCardComparator {
    @Test
    fun whenCompareIsCalledAndValueOfCard1IsGreaterThanCard2_shouldReturn1() {
        val expected = 1
        val card1 = Card(Suit.CLUBS, Value.KING)
        val card2 = Card(Suit.CLUBS, Value.JACK)

        val uut = CardComparator()
        val actual = uut.compare(card1, card2)

        assertEquals(expected, actual)
    }

    @Test
    fun whenCompareIsCalledAndValueOfCard1IsLessThanCard2_shouldReturnNegative1() {
        val expected = -1
        val card1 = Card(Suit.CLUBS, Value.JACK)
        val card2 = Card(Suit.CLUBS, Value.KING)

        val uut = CardComparator()
        val actual = uut.compare(card1, card2)

        assertEquals(expected, actual)
    }

    @Test
    fun whenCompareIsCalledAndValueOfCard1IsEqualToCard2_shouldReturn0() {
        val expected = 0
        val card1 = Card(Suit.CLUBS, Value.JACK)
        val card2 = Card(Suit.CLUBS, Value.JACK)

        val uut = CardComparator()
        val actual = uut.compare(card1, card2)

        assertEquals(expected, actual)
    }
}