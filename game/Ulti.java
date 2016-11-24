package game;

/**
 *
 * @author Anna
 */
public class Ulti {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // just testing
        Player p1 = new Player("Anastasia");
        Player p2 = new Player("Bellamy");
        Player p3 = new Player("Casey");
        Game game = new Game(p1, p2, p3, true);
        
    }
    
}
