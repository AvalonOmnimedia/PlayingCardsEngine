import com.avalonomnimedia.playingcardsengine.Card
import com.avalonomnimedia.playingcardsengine.Deck
import com.avalonomnimedia.playingcardsengine.Suit
import com.avalonomnimedia.playingcardsengine.Value

class PracticeDeck : Deck() {
    override val cards: List<Card> = listOf(
        Card(Suit.HEARTS, Value.ACE),
        Card(Suit.HEARTS, Value.TWO),
        Card(Suit.HEARTS, Value.THREE),
        Card(Suit.HEARTS, Value.FOUR),
        Card(Suit.HEARTS, Value.FIVE),
        Card(Suit.HEARTS, Value.SIX),
        Card(Suit.HEARTS, Value.SEVEN)
    )
}

class FourCardDeck(suit: Suit = Suit.HEARTS) : Deck() {
    override val cards: List<Card> = listOf(
        Card(suit, Value.ACE),
        Card(suit, Value.TWO),
        Card(suit, Value.THREE),
        Card(suit, Value.FOUR)
    )

}