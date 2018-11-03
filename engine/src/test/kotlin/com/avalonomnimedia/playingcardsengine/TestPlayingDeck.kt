import com.avalonomnimedia.playingcardsengine.Card
import com.avalonomnimedia.playingcardsengine.PlayingDeck
import com.avalonomnimedia.playingcardsengine.StandardDeck
import com.avalonomnimedia.playingcardsengine.Suit
import com.avalonomnimedia.playingcardsengine.Value
import org.junit.Assert
import org.junit.Test

class TestPlayingDeck {
    @Test
    fun `when playing deck is created with 1 deck, should have 52 cards`() {
        val uut = PlayingDeck()
        val expected = 52

        uut.add(StandardDeck())
        val actual = uut.count()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when playing deck is created with 2 decks, should have 104 cards`() {
        val uut = PlayingDeck()
        val expected = 104

        uut.add(StandardDeck())
        uut.add(StandardDeck())
        val actual = uut.count()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when playing deck is created with 3 decks, should have 156 cards`() {
        val uut = PlayingDeck()
        val expected = 156

        uut.add(StandardDeck())
        uut.add(StandardDeck())
        uut.add(StandardDeck())
        val actual = uut.count()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when shuffled, cards should be in a different order`() {
        val uut = PlayingDeck()
        val expected = StandardDeck()

        uut.add(expected)
        uut.shuffle()
        val actual = uut

        Assert.assertNotEquals(expected, actual)
    }

    @Test
    fun `when number of cards asked for is 0, should return 0 cards`() {
        val uut = PlayingDeck()
        uut.add(StandardDeck())
        val expected = 0

        val list = uut.takeCards(expected)
        val actual = list.count()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when number of cards asked for is 1, should return 1 card`() {
        val uut = PlayingDeck()
        uut.add(StandardDeck())
        val expected = 1

        val hand = uut.takeCards(expected)
        val actual = hand.count()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when number of cards asked for is 4, should return 4 card`() {
        val uut = PlayingDeck()
        uut.add(StandardDeck())
        val expected = 4

        val hand = uut.takeCards(expected)
        val actual = hand.count()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when a card is taken, should return first card`() {
        val uut = PlayingDeck()
        uut.add(StandardDeck())
        val expected = Card(Suit.DIAMONDS, Value.ACE)

        val actual = uut.takeCard()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when a card is taken, should remove it from deck`() {
        val uut = PlayingDeck()
        uut.add(StandardDeck())
        val expected = 51

        uut.takeCard()
        val actual = uut.count()

        Assert.assertEquals(expected, actual)
    }
}