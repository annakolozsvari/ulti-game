package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.enumerations.*;
import game.exceptions.*;

/**
 *
 * @author Anna
 */
public class Game {
    public List<Card> talon;
    private final Player[] players;
    
    private Player nextPlayer;
    private GameState state;
    
    private Bid bid;
    private Player bidder;
    
    public Player[] trickWinner;
    public Boolean won;
    private Suit trump;
    
    //Order of bidding: p0, p1, p2 (p0 starts)
    public Game(Player p0, Player p1, Player p2) {
        talon = new ArrayList<>();
        players = new Player[] {p0, p1, p2};
        
        nextPlayer = p0;
        state = GameState.Deal;
    }
    
    private void setNextPlayer() {
        if(nextPlayer == players[0]) {
            nextPlayer = players[1];
        } else if (nextPlayer == players[1]) {
            nextPlayer = players[2];
        } else if (nextPlayer == players[2]) {
            nextPlayer = players[0];
        } else {
        }
    }
    
    public void deal() {
        for(Suit suit : Suit.values()) {
            for(Rank rank : Rank.values()) {
                talon.add(new Card(suit, rank));
            }
        }
        Random randomGenerator = new Random();
        for(int i = 32; i>0; i--) {
            int randomIndex = randomGenerator.nextInt(i);
            if(i>20) {
                players[0].cards.add(talon.remove(randomIndex));
            } else if(i>10) {
                players[1].cards.add(talon.remove(randomIndex));
            } else {
                players[2].cards.add(talon.remove(randomIndex));
            }
        }
        
        state = GameState.Auction;
    }
    
    public void bid(Player player, Bid actualBid) {
        
        if(!bid.higher(actualBid)) {
            if(actualBid == Bid.Pass) {
                if(bidder == player) {
                    state = GameState.Playing;
                }
                return;
            }
        }
        
        bid = actualBid;
        bidder = player;
        
        setNextPlayer();
    }
    
    public void play(Player player, Card card) {
        
        /*
        List<Card> playedCards = new ArrayList<>();
        for(int trickCount = 0; trickCount<10; trickCount++) {
            for(int i=0; i<3; i++) {
                playedCards.add(card);
                setNextPlayer();
            }
            
            Card winnerCard = decideTrickWinner(playedCards.get(0), playedCards.get(1), playedCards.get(2));
            int index = playedCards.indexOf(winnerCard);
            nextPlayer = trickWinner[trickCount] = players[index];
            
            trickWinner[trickCount].cardsWon.addAll(playedCards);
            playedCards.clear();
        }
        */
        state = GameState.Over;
        evaluateGame();
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
