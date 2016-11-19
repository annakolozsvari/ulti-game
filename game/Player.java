package game;

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
    
    public Player(String n) {
        name = n;
        cards = new ArrayList<>();
        cardsWon = new ArrayList<>();
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
}
