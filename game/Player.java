package ultigame.game;

import ultigame.game.enumerations.Rank;
import ultigame.game.enumerations.Suit;
import ultigame.game.exceptions.IllegalCardPlayedException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anna
 */
public class Player {
    public final String name;
    public List<Card> cards;
    public List<Card> cardsWon;
    private int marriageNumber;
    private int trumpMarriageNumber;
    
    public Player(String n) {
        name = n;
        cards = new ArrayList<>();
        cardsWon = new ArrayList<>();
    }
    
    public boolean hasSuit(Suit suit) {
        for(Card card : cards) {
            if(card.getSuit() == suit) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasHigherInSuit(Rank rank, Suit suit, boolean trumpGame) {
        for(Card card : cards) {
            if(card.getSuit() == suit && card.getRank().higher(rank, trumpGame)) {
                return true;
            }
        }
        return false;
    }
    
    public void countMarriages(Suit trump) {
        marriageNumber = 0;
        trumpMarriageNumber = 0;
        
        for(Card card : cards) {
            if(card.getRank() == Rank.Upper) {
                for(Card c : cards) {
                    if(c.getRank()==Rank.King && c.getSuit()==card.getSuit()) {
                        if(c.getSuit() == trump) {
                            trumpMarriageNumber++;
                        } else {
                            marriageNumber++;
                        }
                    }
                }
            }
        }
    }
    
    public void validate(Card card, List<Card> playedCards, Suit trump) {
        if(!cards.contains(card)) {
            throw new IllegalCardPlayedException("You cannot play a card you don't have.");
        }
        if(playedCards.isEmpty()) {
            return;
        }
        
        Suit suit = playedCards.get(0).getSuit();
        boolean trumpGame = (trump != null);
        
        if(hasSuit(suit)) {
            //if possible, suit must be followed
            if(card.getSuit() != suit) {
                throw new IllegalCardPlayedException("If you have suit, you must play suit.");
            }
            
            if(playedCards.size()>1) {
                //can't go higher than trump
                if(suit != trump && playedCards.get(1).getSuit() == trump) {
                    return;
                }
                if(playedCards.get(1).getSuit() == suit) {
                    boolean higher = card.getRank().higher(playedCards.get(1).getRank(), trumpGame);
                    boolean hasHigher = hasHigherInSuit(playedCards.get(1).getRank(), suit, trumpGame);
                    //if possible, must be higher than the second card
                    if(!higher && hasHigher) {
                        throw new IllegalCardPlayedException(
                                "If you have higher than the cards played, you must play higher.");
                    }
                }
            }
            
            boolean higher = card.getRank().higher(playedCards.get(0).getRank(), trumpGame);
            boolean hasHigher = hasHigherInSuit(playedCards.get(0).getRank(), suit, trumpGame);
            //if possible, must be higher than the first card
            if(!higher && hasHigher) {
                throw new IllegalCardPlayedException(
                        "If you have higher than the cards played, you must play higher.");
            }
        } else if(hasSuit(trump)) {
            //if following the suit is not possible, trump must be played
            if(card.getSuit() != trump) {
                throw new IllegalCardPlayedException("If you have trump, you must play trump (or suit).");
            }
            
            if(playedCards.size()>1 && playedCards.get(1).getSuit() == trump) {
                boolean higher = card.getRank().higher(playedCards.get(1).getRank(), trumpGame);
                boolean hasHigher = hasHigherInSuit(playedCards.get(1).getRank(), trump, trumpGame);
                //if possible, must be higher
                if(!higher && hasHigher) {
                    throw new IllegalCardPlayedException(
                            "If you have higher than the cards played, you must play higher.");
                }
            }
        }
    }
    
    public int getMarriageNumber() {
        return marriageNumber;
    }
    
    public int getTrumpMarriageNumber() {
        return trumpMarriageNumber;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Player) {
            Player otherPlayer = (Player)obj;
            return (name.equals(otherPlayer.name) && cards.equals(otherPlayer.cards) 
                    && cardsWon.equals(otherPlayer.cardsWon));
        } else {
            return (this == obj);
        }
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Card> getCardsWon() {
        return cardsWon;
    }
}
