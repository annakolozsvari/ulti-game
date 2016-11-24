package ultigame.game;

import ultigame.game.enumerations.Contract;

/**
 *
 * @author Anna
 */
public class Bid {

    public final boolean trumpGame;
    public final boolean pass;
    public final boolean hearts;

    public final Contract noTrumpValue;
    public final Contract ulti;
    public final Contract hundred;
    public final Contract durchmarsAces;

    public final int value;
    public final int components;

    Bid(Contract contract, boolean hearts) {
        trumpGame = !(contract == Contract.Betli || contract == Contract.PlainDurchmars);

        String bidName = contract.toString();

        noTrumpValue = !trumpGame ? contract : null;
        ulti = bidName.contains("Ulti") ? Contract.Ulti : null;
        hundred = bidName.contains("FortyHundred")
                ? Contract.FortyHundred
                : (bidName.contains("TwentyHundred")
                ? Contract.TwentyHundred
                : null);
        durchmarsAces = bidName.contains("Durchmars") && trumpGame
                ? Contract.Durchmars
                : (bidName.contains("FourAces")
                ? Contract.FourAces
                : null);
        
        pass = (contract == Contract.Pass) ||
                (trumpGame && hundred==null && durchmarsAces!=Contract.Durchmars);
        this.hearts = hearts;
        
        value = calculateValue();
        components = countComponents();
    }

    private int calculateValue() {
        int bidValue;
        if (trumpGame) {
            bidValue = Contract.values.get(ulti)
                + Contract.values.get(hundred)
                + Contract.values.get(durchmarsAces);
            if (pass) {
                bidValue += Contract.values.get(Contract.Pass);
            }
        } else {
            bidValue = Contract.values.get(noTrumpValue);
        }
        bidValue = hearts ? bidValue * 2 : bidValue;
        return bidValue;
    }

    private int countComponents() {
        int comps = (!trumpGame) ?
                1 :
                ((pass) ? 1 : 0) +
                ((ulti != null) ? 1 : 0) + 
                ((hundred != null) ? 1 : 0) + 
                ((durchmarsAces != null) ? 1 : 0);
        
        return comps;
    }

    //Is this bid higher?
    public boolean higher(Bid otherBid) {
        if(value > otherBid.value) {
            return true;
        }
        if((value == otherBid.value) && (components < otherBid.components)) {
            return true;
        }
        //With the same value and the same amount of components, Ulti is always higher then Four Aces.
        if((value == otherBid.value) && (hundred==otherBid.hundred) && (hearts == otherBid.hearts) &&
                (ulti == Contract.Ulti) && (otherBid.ulti == null) &&
                (durchmarsAces == null) && (otherBid.durchmarsAces == Contract.FourAces)) {
            return true;
        }
        return false;
    }

    public boolean isTrumpGame() {
        return trumpGame;
    }

    public boolean isPass() {
        return pass;
    }

    public boolean isHearts() {
        return hearts;
    }

    public Contract getNoTrumpValue() {
        return noTrumpValue;
    }

    public Contract getUlti() {
        return ulti;
    }

    public Contract getHundred() {
        return hundred;
    }

    public Contract getDurchmarsAces() {
        return durchmarsAces;
    }

    public int getValue() {
        return value;
    }

    public int getComponents() {
        return components;
    }
}
