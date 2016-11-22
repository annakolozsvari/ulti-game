package game.enumerations;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Anna
 */
public enum Contract {
    Pass, FortyHundred, FourAces, Ulti, Betli, PlainDurchmars, Durchmars, TwentyHundred,
    UltiFourAces, UltiDurchmars, FortyHundredUlti, TwentyHundredUlti, FortyHundredFourAces,
    FortyHundredDurchmars, TwentyHundredFourAces, TwentyHundredDurchmars,
    FortyHundredUltiFourAces, FortyHundredUltiDurchmars, TwentyHundredUltiFourAces,
    TwentyHundredUltiDurchmars;

    public static Map<Contract, Integer> values = initializeValues();

    private static Map<Contract, Integer> initializeValues() {
        Map<Contract, Integer> map = new HashMap<>();
        map.put(null, 0);
        map.put(Contract.Pass, 1);
        map.put(Contract.FortyHundred, 4);
        map.put(Contract.FourAces, 4);
        map.put(Contract.Ulti, 4);
        map.put(Contract.Betli, 5);
        map.put(Contract.PlainDurchmars, 6);
        map.put(Contract.Durchmars, 6);
        map.put(Contract.TwentyHundred, 8);
        return map;
    }
}
