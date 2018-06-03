package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;

import java.util.List;

public class SocketFromServerToClient implements IFromServerToClient {
    @Override
    public void showRoom(List<String> players) {

    }

    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) throws BrokenConnectionException {

    }

    @Override
    public void choseWpId() throws BrokenConnectionException {

    }

    @Override
    public void showCommonBoard(List<SetUpInformationUnit> draftPool, SimplifiedWindowPatternCard wp) throws BrokenConnectionException {

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

    @Override
    public void showNotice(String notice) throws BrokenConnectionException {

    }

}
