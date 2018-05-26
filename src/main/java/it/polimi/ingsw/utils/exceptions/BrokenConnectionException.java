package it.polimi.ingsw.utils.exceptions;

/**
 * This exception is thrown if the connection between a server and a client drops.
 */
public class BrokenConnectionException extends Exception {

    public BrokenConnectionException() {
        super();
    }
}
