package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.network.IFromClientToServer;

import java.util.List;
import java.util.Map;

public interface IViewMaster {

    void createConnection(IViewMaster viewMaster);

    void showRoom(List<String> players);

    void showPrivateObjective(int idPrivateObj);

    void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp);

    void choseWpId();

    void setCommonBoard(Map<String,SimplifiedWindowPatternCard> players, int [] idPubObj, int[] idTool);

    void setDraft(List<SetUpInformationUnit> draft);

    void setFavorToken(int nFavTokens);

   void showCommand(List<Commands> commands);

    void addOnOwnWp(SetUpInformationUnit unit);

    void removeOnOwnWp(SetUpInformationUnit unit);

    void addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit);

    void removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit);

    void addOnDraft(SetUpInformationUnit info);

    void removeOnDraft(SetUpInformationUnit info);

    void addOnRoundTrack(SetUpInformationUnit info);

    void removeOnRoundTrack(SetUpInformationUnit info);

    void updateFavTokenPlayer(int nFavorToken);

    void showNotice(String notice);

    IFromClientToServer getServer();

    void updateToolCost(int idSlot, int cost);

    void showRank(String[] playerNames, int[] scores);

    void forceLogOut();
}
