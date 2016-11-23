package ultigame.game.exceptions;

/**
 *
 * @author Anna
 */
public class IllegalBidException extends IllegalStateException {

    /**
     * Creates a new instance of <code>IllegalBidException</code> without detail
     * message.
     */
    public IllegalBidException() {
        super();
    }

    /**
     * Constructs an instance of <code>IllegalBidException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public IllegalBidException(String msg) {
        super(msg);
    }
}
