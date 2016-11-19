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
    
    private Suit trump;
    private int trickCount = 0;
    private List<Card> playedCards;
    public Player[] trickWinner;
    public Boolean won;
    
    //Order of bidding: p0, p1, p2 (p0 starts)
    public Game(Player p0, Player p1, Player p2) {
        talon = new ArrayList<>();
        playedCards = new ArrayList<>();
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
            throw new IllegalPlayerException();
        }
    }
    
    public void deal() {
        if(state != GameState.Deal) {
            throw new IllegalGameStateException("You cannot deal in the " + state + " state." );
        }
        
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
        if(state != GameState.Auction) {
            throw new IllegalGameStateException("You cannot bid in the " + state + " state." );
        }
        if(player != nextPlayer) {
            throw new IllegalPlayerException(player.name + " cannot bid, it's " + nextPlayer.name + "'s turn.");
        }
        
        if(!bid.higher(actualBid)) {
            if(actualBid == Bid.Pass) {
                if(bidder == player) {
                    state = GameState.Playing;
                }
                return;
            }
            throw new IllegalBidException("The last bid was " + bid + ", you have to bid higher (or pass).");
        }
        
        bid = actualBid;
        bidder = player;
        
        setNextPlayer();
    }
    
    public void play(Player player, Card card) {
        if(state != GameState.Playing) {
            throw new IllegalGameStateException("You cannot play in the " + state + " state." );
        }
        if(player != nextPlayer) {
            throw new IllegalPlayerException(player.name + " cannot play, it's " + nextPlayer.name + "'s turn.");
        }
        
        playedCards.add(card);
        setNextPlayer();
        
        if(playedCards.size() == 3) {
            trickCount++;
            
            Card winnerCard = decideTrickWinner(playedCards.get(0), playedCards.get(1), playedCards.get(2));
            int index = playedCards.indexOf(winnerCard);
            nextPlayer = trickWinner[trickCount] = players[index];
            
            trickWinner[trickCount].cardsWon.addAll(playedCards);
            playedCards.clear();
        }
        
        if(trickCount == 10) {
            state = GameState.Over;
            evaluateGame();
        }
        /*
            
            
        */
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
