package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.IServer;
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
    public void login(String playerName, String gameMode) {
        controllerMaster.getConnectedPlayers().put(playerName, fromServerToClient);
        controllerMaster.getStartGameState().login(playerName, gameMode);
        System.err.println("Client " + playerName + " just connected!");
    }

    @Override
    public void exitGame(String playerName) {

    }
}
