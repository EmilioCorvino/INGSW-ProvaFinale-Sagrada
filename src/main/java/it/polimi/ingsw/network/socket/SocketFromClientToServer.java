package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.network.IFromClientToServer;

public class SocketFromClientToServer implements IFromClientToServer {
    @Override
    public void login(int gameMode, String playerName) {

    }

    @Override
    public void windowPatternCardRequest(int idMap) throws BrokenConnectionException {

    }

    @Override
    public void defaultMoveRequest() throws BrokenConnectionException {

    }

    @Override
    public void performMove(SetUpInformationUnit info) throws BrokenConnectionException {

    }

    @Override
    public void exitGame(String playerName) {

    }

}
