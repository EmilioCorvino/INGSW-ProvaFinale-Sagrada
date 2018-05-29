package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.die.diecontainers.DiceDraftPool;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.network.IFromServerToClient;

import java.util.List;

public class SocketFromServerToClient implements IFromServerToClient {
    @Override
    public void showRoom(List<String> players) {

    }

    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) throws BrokenConnectionException {

    }

    @Override
    public void showCommonBoard(DiceDraftPool draft, SimplifiedWindowPatternCard wp) throws BrokenConnectionException {

    }

    @Override
    public void showCommand() throws BrokenConnectionException {

    }

    @Override
    public void giveProperObjectToFill(SetUpInformationUnit setInfoUnit) throws BrokenConnectionException {

    }

    @Override
    public void showUpdatedWp(String username, SetUpInformationUnit info) throws BrokenConnectionException {

    }

}
