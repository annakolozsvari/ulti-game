package ultigame.game.exceptions;

/**
 *
 * @author Anna
 */
public class IllegalCardPlayedException extends IllegalArgumentException {

    /**
     * Creates a new instance of <code>IllegalCardPlayedException</code> without
     * detail message.
     */
    public IllegalCardPlayedException() {
        super();
    }

    /**
     * Constructs an instance of <code>IllegalCardPlayedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public IllegalCardPlayedException(String msg) {
        super(msg);
    }
}
