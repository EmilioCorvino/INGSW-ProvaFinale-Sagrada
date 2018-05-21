package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.IServer;

public class RmiServer implements IServer {

    private final ControllerMaster controllerMaster;

    public RmiServer(ControllerMaster controllerMaster) {
        this.controllerMaster = controllerMaster;
    }


    @Override
    public void login(String playerName, String gameMode) {
        controllerMaster.getStartGameState().login(playerName);
    }

    @Override
    public void exitGame(String playerName) {

    }
}
