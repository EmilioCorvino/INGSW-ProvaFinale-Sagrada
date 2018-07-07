package it.polimi.ingsw.common.utils.exceptions;

/**
 * This exception is thrown if a player tries to connect while the match is running.
 * @see it.polimi.ingsw.server.controller.WaitingRoom
 */
public class MatchAlreadyStartedException extends Exception {

    public MatchAlreadyStartedException() {
        super();
    }
}
