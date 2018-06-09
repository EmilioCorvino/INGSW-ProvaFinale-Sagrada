package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.controller.simplified_view.InformationUnit;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;

import java.util.List;
import java.util.Map;

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
    public void setCommonBoard(Map<String, SimplifiedWindowPatternCard> players, int[] idPubObj, int[] idTool) throws BrokenConnectionException {

    }

    @Override
    public void setDraft(List<SetUpInformationUnit> draft) throws BrokenConnectionException {

    }

    @Override
    public void setPlayer(int nFavTokens, int idPrivateObj) throws BrokenConnectionException {

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
    public void updateOwnWp(SetUpInformationUnit unit) throws BrokenConnectionException {

    }

    @Override
    public void updateOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws BrokenConnectionException {

    }

    @Override
    public void updateDraft(InformationUnit info) throws BrokenConnectionException {

    }

    @Override
    public void updateFavTokenPlayer(int nFavorToken) throws BrokenConnectionException {

    }

    @Override
    public void showNotice(String notice) throws BrokenConnectionException {

    }

    @Override
    public void setMyTurn(boolean myTurn) throws BrokenConnectionException {

    }

}
