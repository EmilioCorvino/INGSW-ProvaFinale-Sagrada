package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.IServer;
import it.polimi.ingsw.exceptions.TooManyUsersException;
import it.polimi.ingsw.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.network.IFromServerToClient;

import java.rmi.server.Unreferenced;

public class RmiServer implements IServer, Unreferenced {

    private final ControllerMaster controllerMaster;
    private IFromServerToClient fromServerToClient;

    public RmiServer(ControllerMaster controllerMaster) {
        this.controllerMaster = controllerMaster;
    }

    @Override
    public synchronized void establishConnection(IFromServerToClient fromServerToClient) {
        this.fromServerToClient = fromServerToClient;
    }

    @Override
    public void login(String gameMode, String playerName) throws UserNameAlreadyTakenException, TooManyUsersException {
        if(controllerMaster.getStartGameState().isFull()) {
           throw new TooManyUsersException("The room is full");
        }
        if(controllerMaster.getStartGameState().checkLogin(playerName)) {
            controllerMaster.getConnectedPlayers().put(playerName, fromServerToClient);
        } else {
            throw new UserNameAlreadyTakenException("Player is already logged in");
        }
        controllerMaster.getStartGameState().login(gameMode);
        System.err.println("Client " + playerName + " just connected!");
    }

    @Override
    public void exitGame(String playerName) {

    }

    @Override
    public void unreferenced() {

    }
}
