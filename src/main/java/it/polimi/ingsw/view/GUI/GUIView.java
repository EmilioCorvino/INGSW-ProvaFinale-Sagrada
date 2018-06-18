package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.view.IViewMaster;

import java.util.List;
import java.util.Map;

public class GUIView implements IViewMaster {

    /**
     * The network interface for the connection.
     */
    IFromClientToServer fromClientToServer;








    @Override
    public void createConnection(IViewMaster viewMaster) {

    }

    @Override
    public void showRoom(List<String> players) {

    }

    @Override
    public void showPrivateObjective(int idPrivateObj) {

    }

    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) {

    }

    @Override
    public void choseWpId() {

    }

    @Override
    public void setCommonBoard(Map<String, SimplifiedWindowPatternCard> players, int[] idPubObj, int[] idTool) {

    }

    @Override
    public void setDraft(List<SetUpInformationUnit> draft) {

    }

    @Override
    public void setFavorToken(int nFavTokens) {

    }

    @Override
    public void showCommand(List<Commands> commands) {

    }

    @Override
    public void addOnOwnWp(SetUpInformationUnit unit) {

    }

    @Override
    public void removeOnOwnWp(SetUpInformationUnit unit) {

    }

    @Override
    public void addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) {

    }

    @Override
    public void removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) {

    }

    @Override
    public void addOnDraft(SetUpInformationUnit info) {

    }

    @Override
    public void removeOnDraft(SetUpInformationUnit info) {

    }

    @Override
    public void addOnRoundTrack(SetUpInformationUnit info) {

    }

    @Override
    public void removeOnRoundTrack(SetUpInformationUnit info) {

    }

    @Override
    public void updateFavTokenPlayer(int nFavorToken) {

    }

    @Override
    public void showNotice(String notice) {

    }

    @Override
    public IFromClientToServer getServer() {
        return null;
    }

    @Override
    public void updateToolCost(int idSlot, int cost) {

    }

    @Override
    public void showRank(String[] playerNames, int[] scores) {

    }
}
