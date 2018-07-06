package it.polimi.ingsw.common.network.socket;

import it.polimi.ingsw.common.network.IFromClientToServer;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;

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

    @Override
    public void reconnect() throws BrokenConnectionException {

    }

}
