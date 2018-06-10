package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplified_view.InformationUnit;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.network.IFromClientToServer;

import java.util.List;
import java.util.Map;

public abstract class AViewMaster {

    public abstract void createConnection(AViewMaster viewMaster);

    public abstract void showRoom(List<String> players);

    public abstract void showPrivateObjective(int idPrivateObj);

    public abstract void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp);

    public abstract void choseWpId();

    public abstract void setCommonBoard(Map<String,SimplifiedWindowPatternCard> players, int [] idPubObj, int[] idTool);

    public abstract void setDraft(List<SetUpInformationUnit> draft);

    public abstract void setFavorToken(int nFavTokens);

   public abstract void showCommand(List<Commands> commands);

    // DA CANCELLARE
    public abstract void showUpdatedWp(String username, SetUpInformationUnit info);

    public abstract void updateOwnWp(SetUpInformationUnit unit);

    public abstract void updateOtherPlayerWp(String userName, SetUpInformationUnit infoUnit);

    public abstract void updateDraft(InformationUnit info);

    public abstract void updateFavTokenPlayer(int nFavorToken);

    public abstract void showNotice(String notice);

    public abstract IFromClientToServer getServer();

    public abstract void setMyTurn(boolean myTurn);
}
