package it.polimi.ingsw.common.network.socket;

import it.polimi.ingsw.common.Commands;
import it.polimi.ingsw.common.network.IFromServerToClient;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.simplifiedview.SimplifiedWindowPatternCard;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;

import java.util.ArrayList;
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
    public void showPrivateObjective(int privateObjCardId) throws BrokenConnectionException {

    }

    @Override
    public void setCommonBoard(Map<String, SimplifiedWindowPatternCard> players, int[] idPubObj, int[] idTool) throws BrokenConnectionException {

    }

    @Override
    public void setDraft(List<SetUpInformationUnit> draft) throws BrokenConnectionException {

    }

    @Override
    public void setFavorToken(int nFavTokens) throws BrokenConnectionException {

    }

    @Override
    public void setRestoredWindowPatternCards(Map<String, List<SetUpInformationUnit>> diceToRestore) throws BrokenConnectionException {

    }

    @Override
    public void setRestoredRoundTrack(List<ArrayList<SetUpInformationUnit>> roundTrackToRestore) throws BrokenConnectionException {

    }

    @Override
    public void showCommand(List<Commands> commands) throws BrokenConnectionException {

    }

    @Override
    public void addOnOwnWp(SetUpInformationUnit unit) throws BrokenConnectionException {

    }

    @Override
    public void removeOnOwnWp(SetUpInformationUnit unit) throws BrokenConnectionException {

    }

    @Override
    public void addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws BrokenConnectionException {

    }

    @Override
    public void removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws BrokenConnectionException {

    }

    @Override
    public void addOnDraft(SetUpInformationUnit info) throws BrokenConnectionException {

    }

    @Override
    public void removeOnDraft(SetUpInformationUnit info) throws BrokenConnectionException {

    }

    @Override
    public void addOnRoundTrack(SetUpInformationUnit info) throws BrokenConnectionException {

    }

    @Override
    public void removeOnRoundTrack(SetUpInformationUnit info) throws BrokenConnectionException {

    }

    @Override
    public void updateFavTokenPlayer(int nFavorToken) throws BrokenConnectionException {

    }

    @Override
    public void updateToolCost(int idSlot, int cost) throws BrokenConnectionException {

    }

    @Override
    public void showDie(SetUpInformationUnit informationUnit) throws BrokenConnectionException {

    }

    @Override
    public void showRank(String[] playerNames, int[] scores) throws BrokenConnectionException {

    }

    @Override
    public void showNotice(String notice) throws BrokenConnectionException {

    }

    @Override
    public void forceLogOut() throws BrokenConnectionException {

    }

}
