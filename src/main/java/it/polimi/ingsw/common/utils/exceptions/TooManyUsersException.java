package it.polimi.ingsw.common.utils.exceptions;

/**
 * This exception is thrown if a layer tries to connect to the {@link it.polimi.ingsw.server.controller.WaitingRoom}
 * when there is already the maximum amount of players inside.
 * @see it.polimi.ingsw.server.controller.WaitingRoom
 */
public class TooManyUsersException extends Exception {

    public TooManyUsersException() {
        super();
    }
}
