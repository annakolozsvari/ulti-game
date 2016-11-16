package game;

import java.util.ArrayList;
import java.util.List;
import game.enumerations.Action;
import game.enumerations.Bid;
import game.enumerations.Suit;

/**
 *
 * @author Anna
 */
public class Game {
    public List<Card> talon;
    private final Player[] players;
    
    private Bid bid;
    private Player bidder;
    
    public Player[] trickWinner;
    public Boolean won;
    private Suit trump;
    
    
    public Game(Player p1, Player p2, Player p3){
        talon = new ArrayList<>();
        players = new Player[] {p1, p2, p3};
    }
    
    //After the constructor, this should be called.
    public void start() {
        //deal
        deal();
        
        //auction
        boolean bidding = true;
        bid = players[2].bid(talon);
        bidder = players[2];
        while(bidding) {
            for(Player player : players) {
                if(player.decide(bid, bidder) == Action.PickUp) {
                    bid = player.bid(talon);
                    bidder = player;
                } else {
                    if(bidder == player) {
                        bidding = false;
                    }
                }
            }
        }
        
        //game
        List<Card> playedCards = new ArrayList<>();
        for(int trickCount = 0; trickCount<10; trickCount++) {
            for(int i=0; i<3; i++) {
                Card playedCard = players[i].play(playedCards);
                playedCards.add(playedCard);
            }
            
            Card winnerCard = decideTrickWinner(playedCards.get(0), playedCards.get(1), playedCards.get(2));
            int index = playedCards.indexOf(winnerCard);
            trickWinner[trickCount] = players[index];
            
            playedCards.clear();
        }
        
        //scoring
        evaluateGame();
    }
    
    //Deal the cards to the players.
    private void deal() {
        //TODO
    }
    
    //Decide who won the actual trick.
    private Card decideTrickWinner(Card c1, Card c2, Card c3) {
        //TODO
        return null;
    }
    
    //Decide who won the game.
    private void evaluateGame() {
        //TODO
    }
}
