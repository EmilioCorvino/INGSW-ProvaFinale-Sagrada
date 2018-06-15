package it.polimi.ingsw.utils.exceptions;

import java.io.IOException;

/**
 * This exception is thrown if the connection between a server and a client drops.
 */
public class BrokenConnectionException extends IOException {

    public BrokenConnectionException() {
        super();
    }
}
