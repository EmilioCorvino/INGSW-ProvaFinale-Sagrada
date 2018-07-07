package it.polimi.ingsw.common.utils.exceptions;

/**
 * This exception is thrown if a player tries to connect with the same name of another player already present in
 * the {@link it.polimi.ingsw.server.controller.WaitingRoom}.
 * @see it.polimi.ingsw.server.controller.WaitingRoom
 */
public class UserNameAlreadyTakenException extends Exception {

    public UserNameAlreadyTakenException(){
        super();
    }
}
