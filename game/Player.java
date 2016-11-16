package game;

import java.util.ArrayList;
import java.util.List;
import game.enumerations.Action;
import game.enumerations.Bid;

/**
 *
 * @author Anna
 */
public class Player {
    public final String name;
    public List<Card> cards;
    public List<Card> cardsWon;
    
    public Player(String n) {
        name = n;
        cards = new ArrayList<>();
        cardsWon = new ArrayList<>();
    }
    
    //Decide to pick up talon and bid, or pass.
    public Action decide(Bid bid, Player bidder) {
        //TODO
        return null;
    }
    
    //Bid.
    public Bid bid(List<Card> talon) {
        //TODO
        cards.addAll(talon);
        talon.clear();
        //talon.add(selectedCards);
        return null;
    }
    
    //Having started the game: play a card in the current trick.
    public Card play(List<Card> cardsPlayedBefore) {
        //TODO
        return null;
    }
}
