package ultigame.game.exceptions;

/**
 *
 * @author Anna
 */
public class IllegalGameStateException extends IllegalStateException {

    /**
     * Creates a new instance of <code>IllegalGameStateException</code> without
     * detail message.
     */
    public IllegalGameStateException() {
        super();
    }

    /**
     * Constructs an instance of <code>IllegalGameStateException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public IllegalGameStateException(String msg) {
        super(msg);
    }
}
