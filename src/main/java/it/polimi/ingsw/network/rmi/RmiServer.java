package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.IServer;
import it.polimi.ingsw.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.network.IFromServerToClient;

public class RmiServer implements IServer {

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
    public void login(String playerName, String gameMode) throws UserNameAlreadyTakenException {
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
}
