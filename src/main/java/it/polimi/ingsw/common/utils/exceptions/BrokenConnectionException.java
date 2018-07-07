package it.polimi.ingsw.common.utils.exceptions;

import java.io.IOException;

/**
 * This exception is thrown if the connection between a server and a client drops. It is used to wrap both
 * {@link java.rmi.RemoteException} and any exception thrown by Socket.
 */
public class BrokenConnectionException extends IOException {

    public BrokenConnectionException() {
        super();
    }
}
