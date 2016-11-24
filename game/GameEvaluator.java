package game;

import game.enumerations.Contract;
import game.enumerations.Rank;
import game.enumerations.Suit;
import java.util.List;

/**
 *
 * @author Anna
 */
public class GameEvaluator {

    public static int evaluate(Bid bid, Player bidder, List<Player> opponents, Suit trump,
            Player[] trickWinner, Card lastTrickWinnerCard) {

        int score = 0;

        if (!bid.trumpGame) {
            if (bid.noTrumpValue == Contract.Betli) {
                
                score += betliCompleted(bidder, trickWinner)
                        ? Contract.values.get(Contract.Betli)
                        : -(Contract.values.get(Contract.Betli));
                
            } else if (bid.noTrumpValue == Contract.PlainDurchmars) {
                
                score += durchmarsCompleted(bidder, trickWinner)
                        ? Contract.values.get(Contract.PlainDurchmars)
                        : -(Contract.values.get(Contract.PlainDurchmars));
                
            }
        } else {
            int bidderPoints = countBidderPoints(bidder, trickWinner[9]);
            int opponentPoints = countOpponentPoints(opponents, trickWinner[9]);

            if (bid.pass) {
                
                score += passCompleted(bidder, opponents, bidderPoints, opponentPoints)
                        ? Contract.values.get(Contract.Pass)
                        : -(Contract.values.get(Contract.Pass));
                
            }
            if (bid.ulti == Contract.Ulti) {
                
                score += ultiCompleted(lastTrickWinnerCard, trump, trickWinner[9], bidder)
                        ? Contract.values.get(Contract.Ulti)
                        : -(Contract.values.get(Contract.Ulti));
                
            }
            if (bid.hundred == Contract.FortyHundred) {
                
                score += fortyHundredCompleted(bidder, bidderPoints)
                        ? Contract.values.get(Contract.FortyHundred)
                        : -(Contract.values.get(Contract.FortyHundred));
                
            } else if (bid.hundred == Contract.TwentyHundred) {
                
                score += twentyHundredCompleted(bidder, bidderPoints)
                        ? Contract.values.get(Contract.TwentyHundred)
                        : -(Contract.values.get(Contract.TwentyHundred));
                
            }

            if (bid.durchmarsAces == Contract.FourAces) {
                
                score += fourAcesCompleted(bidder)
                        ? Contract.values.get(Contract.FourAces)
                        : -(Contract.values.get(Contract.FourAces));
                
            } else if (bid.durchmarsAces == Contract.Durchmars) {
                
                score += durchmarsCompleted(bidder, trickWinner)
                        ? Contract.values.get(Contract.Durchmars)
                        : -(Contract.values.get(Contract.Durchmars));
                
            }
        }

        if (bid.hearts) {
            score *= 2;
        }

        return score;
    }

    private static boolean betliCompleted(Player bidder, Player[] trickWinner) {
        for (Player winner : trickWinner) {
            if (winner == bidder) {
                return false;
            }
        }
        return true;
    }

    private static boolean durchmarsCompleted(Player bidder, Player[] trickWinner) {
        for (Player winner : trickWinner) {
            if (winner != bidder) {
                return false;
            }
        }
        return true;
    }

    private static boolean passCompleted(Player bidder, List<Player> opponents, int bidderPoints, int opponentPoints) {
        int bidderPassPoints = bidderPoints
                + (bidder.getMarriageNumber() * 20)
                + (bidder.getTrumpMarriageNumber() * 40);
        int opponentPassPoints = opponentPoints
                + (opponents.get(0).getMarriageNumber() * 20)
                + (opponents.get(0).getTrumpMarriageNumber() * 40)
                + (opponents.get(1).getMarriageNumber() * 20)
                + (opponents.get(1).getTrumpMarriageNumber() * 40);

        return (bidderPassPoints > opponentPassPoints);
    }

    private static boolean ultiCompleted(Card lastTrickWinnerCard, Suit trump, Player lastTrickWinner, Player bidder) {
        return (lastTrickWinnerCard.getSuit() == trump
                && lastTrickWinnerCard.getRank() == Rank.Seven
                && lastTrickWinner == bidder);
    }

    private static boolean fourAcesCompleted(Player bidder) {
        int aces = 0;
        for (Card card : bidder.cardsWon) {
            if (card.getRank() == Rank.Ace) {
                aces++;
            }
        }
        return (aces == 4);
    }

    private static boolean fortyHundredCompleted(Player bidder, int bidderPoints) {
        boolean forty = (bidder.getTrumpMarriageNumber() > 0);
        boolean hundred = (bidderPoints >= 60);
        return (forty && hundred);
    }

    private static boolean twentyHundredCompleted(Player bidder, int bidderPoints) {
        boolean twenty = (bidder.getMarriageNumber() > 0);
        boolean hundred = (bidderPoints >= 80);
        return (twenty && hundred);
    }

    private static int countBidderPoints(Player bidder, Player lastTrickWinner) {
        int bidderPoints = 0;
        for (Card card : bidder.cards) {
            if (card.getRank() == Rank.Ace || card.getRank() == Rank.Ten) {
                bidderPoints += 10;
            }
        }
        if (bidder == lastTrickWinner) {
            bidderPoints += 10;
        }
        return bidderPoints;
    }

    private static int countOpponentPoints(List<Player> opponents, Player lastTrickWinner) {
        int opponentPoints = 0;
        for (Player opponent : opponents) {
            for (Card card : opponent.cards) {
                if (card.getRank() == Rank.Ace || card.getRank() == Rank.Ten) {
                    opponentPoints += 10;
                }
            }
        }
        if (opponents.contains(lastTrickWinner)) {
            opponentPoints += 10;
        }
        return opponentPoints;
    }
}
