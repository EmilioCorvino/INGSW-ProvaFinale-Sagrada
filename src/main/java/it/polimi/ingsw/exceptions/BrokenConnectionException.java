package it.polimi.ingsw.exceptions;

/**
 * This exception is thrown if the connection between a server and a client drops.
 */
public class BrokenConnectionException extends Exception {

    public BrokenConnectionException() {
        super();
    }
}
