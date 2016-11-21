package game;

import game.enumerations.Rank;
import game.enumerations.Suit;
import java.util.List;

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
    
    public static Card getHighestRank(List<Card> cards, boolean trumpGame) {
        if (cards.isEmpty()) {
            return null;
        }
        if (cards.size() == 1) {
            return cards.get(0);
        }
        
        Card highest = cards.get(0);
        for (Card c : cards) {
            boolean higher = c.getRank().higher(highest.getRank(), trumpGame);
            if (higher) {
                highest = c;
            }
        }
        return highest;
    }
}
