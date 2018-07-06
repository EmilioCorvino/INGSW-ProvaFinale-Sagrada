package it.polimi.ingsw.client.view;

import it.polimi.ingsw.common.Commands;
import it.polimi.ingsw.common.network.IFromClientToServer;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.simplifiedview.SimplifiedWindowPatternCard;

import java.util.List;
import java.util.Map;

public interface IViewMaster {

    void createConnection(IViewMaster viewMaster);

    void showRoom(List<String> players);

    void showPrivateObjective(int idPrivateObj);

    void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp);

    void setCommonBoard(Map<String,SimplifiedWindowPatternCard> players, int [] idPubObj, int[] idTool);

    void setDraft(List<SetUpInformationUnit> draft);

    void setFavorToken(int nFavTokens);

    void setRestoredWindowPatternCards(Map<String, List<SetUpInformationUnit>> diceToRestore);

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

    void updateToolCost(int idSlot, int cost);

    void showDie(SetUpInformationUnit informationUnit);

    void setServer(IFromClientToServer server);

    IFromClientToServer getServer();

    void showRank(String[] playerNames, int[] scores);

    void forceLogOut();

    void showNotice(String notice);
}
