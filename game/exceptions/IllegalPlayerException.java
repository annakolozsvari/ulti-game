package ultigame.game.exceptions;

/**
 *
 * @author Anna
 */
public class IllegalPlayerException extends IllegalStateException {

    /**
     * Creates a new instance of <code>IllegalPlayerException</code> without
     * detail message.
     */
    public IllegalPlayerException() {
        super();
    }

    /**
     * Constructs an instance of <code>IllegalPlayerException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public IllegalPlayerException(String msg) {
        super(msg);
    }
}
