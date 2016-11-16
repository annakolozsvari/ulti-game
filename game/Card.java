package game;

import game.enumerations.Rank;
import game.enumerations.Suit;

/**
 *
 * @author Anna
 */
public class Card {
    private final Suit suit;
    private final Rank rank;
    
    public Card(Suit s, Rank r) {
        suit = s;
        rank = r;
    }
    
    public Suit getSuit() {
        return suit;
    }
    
    public Rank getRank() {
        return rank;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Card) {
            Card otherCard = (Card)obj;
            return (suit==otherCard.getSuit() && rank==otherCard.getRank());
        } else {
            return (this == obj);
        }
    }
}
