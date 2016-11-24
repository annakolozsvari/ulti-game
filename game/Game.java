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

    private List<Card> talon;
    private final Player[] players;

    private Player nextPlayer;
    private GameState state;

    private Bid bid;
    private Player bidder;

    private Suit trump;
    private int trickCount = 0;
    private Player[] trickWinner;
    private List<Card> playedCards;
    
    private int score;

    //Order of bidding: p0, p1, p2 (p0 starts)
    public Game(Player p0, Player p1, Player p2) {
        talon = new ArrayList<>();
        playedCards = new ArrayList<>();
        players = new Player[]{p0, p1, p2};

        nextPlayer = p0;
        state = GameState.Deal;
    }

    public void deal() {
        if (state != GameState.Deal) {
            throw new IllegalGameStateException("You cannot deal in the " + state + " state.");
        }

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                talon.add(new Card(suit, rank));
            }
        }
        Random randomGenerator = new Random();
        for (int i = 32; i > 0; i--) {
            int randomIndex = randomGenerator.nextInt(i);
            if (i > 20) {
                players[0].cards.add(talon.remove(randomIndex));
            } else if (i > 10) {
                players[1].cards.add(talon.remove(randomIndex));
            } else {
                players[2].cards.add(talon.remove(randomIndex));
            }
        }

        state = GameState.Auction;
    }

    public void bid(Player player, Bid actualBid, Card card1, Card card2) {
        if (state != GameState.Auction) {
            throw new IllegalGameStateException("You cannot bid in the " + state + " state.");
        }
        if (player != nextPlayer) {
            throw new IllegalPlayerException(player.name + " cannot bid, it's " + nextPlayer.name + "'s turn.");
        }

        if (bid != null && !actualBid.higher(bid)) {
            if (actualBid.pass) {
                if (bidder == player) {
                    state = GameState.Playing;
                }
                return;
            }
            throw new IllegalBidException("The last bid was " + bid + ", you have to bid higher (or pass).");
        }

        bid = actualBid;
        bidder = player;

        player.cards.addAll(talon);
        talon.clear();
        talon.add(card1);
        talon.add(card2);

        setNextPlayer();
    }

    public void setTrump(Suit s) {
        if (state != GameState.Playing) {
            throw new IllegalGameStateException("You cannot name the trump in the " + state + " state.");
        }
        if (trump != null) {
            throw new IllegalStateException("The trump has already been named.");
        }
        trump = s;
        
        for(Player p : players) {
            p.countMarriages(trump);
        }
    }

    public void play(Player player, Card card) {
        if (state != GameState.Playing) {
            throw new IllegalGameStateException("You cannot play in the " + state + " state.");
        }
        if (trump == null && bid.trumpGame) {
            throw new IllegalStateException("You have to name the trump before starting to play.");
        }
        if (player != nextPlayer) {
            throw new IllegalPlayerException(player.name + " cannot play, it's " + nextPlayer.name + "'s turn.");
        }
        
        player.validate(card, playedCards, trump);

        playedCards.add(card);
        player.cards.remove(card);
        setNextPlayer();

        if (playedCards.size() == 3) {
            trickCount++;

            Card winnerCard = decideTrickWinner(playedCards);
            int index = playedCards.indexOf(winnerCard);
            nextPlayer = trickWinner[trickCount] = players[index];

            trickWinner[trickCount].cardsWon.addAll(playedCards);
            playedCards.clear();
            
            if (trickCount == 10) {
                state = GameState.Over;
                
                List<Player> opponents = new ArrayList<>();
                for(Player p : players) {
                    if(p != bidder) {
                        opponents.add(player);
                    }
                } 
                score = GameEvaluator.evaluate(bid, bidder, opponents, trump, trickWinner, winnerCard);
            }
        }
    }

    private void setNextPlayer() {
        if (nextPlayer == players[0]) {
            nextPlayer = players[1];
        } else if (nextPlayer == players[1]) {
            nextPlayer = players[2];
        } else if (nextPlayer == players[2]) {
            nextPlayer = players[0];
        } else {
            throw new IllegalPlayerException();
        }
    }

    private Card decideTrickWinner(List<Card> cards) {
        if(trump != null) {
            List<Card> trumps = new ArrayList<>();
            for (Card card : cards) {
                if (card.getSuit() == trump) {
                    trumps.add(card);
                }
            }
            Card highestTrump = Card.getHighestRank(trumps, true);
            if (highestTrump != null) {
                return highestTrump;
            }
        }

        Suit suit = cards.get(0).getSuit();
        List<Card> suits = new ArrayList<>();
        for (Card card : cards) {
            if (card.getSuit() == suit) {
                suits.add(card);
            }
        }
        
        return Card.getHighestRank(suits, trump!=null);
    }

    public List<Card> getTalon() {
        return talon;
    }

    public Player getTrickWinner(int index) {
        return trickWinner[index];
    }

    //How much did the bidder win (or loose, if negative)?
    //He receives (or pays) this from each opponent - so double in the end.
    public int getScore() {
        return score;
    }
}
