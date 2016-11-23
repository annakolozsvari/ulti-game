package ultigame.game.enumerations;

/**
 *
 * @author Anna
 */
public enum Rank {
    Seven, Eight, Nine, Lower, Upper, King, Ten, Ace;

    //Is this rank higher in a trump game?
    public boolean higher(Rank otherRank) {
        return (this.compareTo(otherRank) > 0);
    }

    //Is this rank higher in a no-trump game?
    public boolean higherNoTrump(Rank otherRank) {
        if (this == Ten) {
            return (otherRank == Seven || otherRank == Eight || otherRank == Nine);
        } else if(otherRank == Ten) {
            return (this == Lower || this == Upper || this == King || this == Ace);
        } else {
            return (this.compareTo(otherRank) > 0);
        }
    }
    
    public boolean higher(Rank otherRank, boolean trumpGame) {
        if(trumpGame) {
            return higher(otherRank);
        } else {
            return higherNoTrump(otherRank);
        }
    }
}
