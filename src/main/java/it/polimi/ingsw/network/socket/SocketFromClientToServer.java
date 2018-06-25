package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.network.IFromClientToServer;

import java.util.List;

public class SocketFromClientToServer implements IFromClientToServer {
    @Override
    public void login(int gameMode, String playerName) {

    }

    @Override
    public void windowPatternCardRequest(int idMap) throws BrokenConnectionException {

    }

    @Override
    public void performDefaultMove(SetUpInformationUnit infoUnit) throws BrokenConnectionException {

    }

    @Override
    public void performToolCardMove(int slotID, List<SetUpInformationUnit> infoUnits) throws BrokenConnectionException {

    }

    @Override
    public void performRestrictedPlacement(SetUpInformationUnit infoUnit) throws BrokenConnectionException {

    }

    @Override
    public void moveToNextTurn() throws BrokenConnectionException {

    }

    @Override
    public void startNewGameRequest() throws BrokenConnectionException {

    }

    @Override
    public void exitGame() {

    }

}
