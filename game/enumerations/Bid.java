package game.enumerations;

/**
 *
 * @author Anna
 */
public enum Bid {
    Pass, FortyHundred, FourAces, Ulti, Betli, Durchmars, TrumpDurchmars, TwentyHundred; 

    //Is this bid higher?
    public boolean higher(Bid otherBid) {
        return (this.compareTo(otherBid) > 0);
    }
    
    public boolean trumpGame() {
        if(this == Betli || this == Durchmars) {
            return false;
        } else {
            return true;
        }
    }
}
