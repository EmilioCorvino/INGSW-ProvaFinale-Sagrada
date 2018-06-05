package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.simplified_view.InformationUnit;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.network.IFromServerToClient;

import java.util.List;
import java.util.Map;

/**
 * This class handles messages arriving from the server, calling methods from {@link AViewMaster}.
 */
public class ClientImplementation implements IFromServerToClient {

    /**
     * View chosen by the user.
     */
    private AViewMaster view;

    public ClientImplementation(AViewMaster view) {
        this.view = view;
    }

    /**
     * Shows the waiting room to the player owning the client.
     * @param players names of the players already connected (including the player itself).
     */
    @Override
    public void showRoom(List<String> players) {
        view.showRoom(players);
    }

    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) {
        view.showMapsToChoose(listWp);
    }

    @Override
    public void choseWpId(){
        view.choseWpId();}
    // DA CANCELLARE
    @Override
    public void showCommonBoard(List<SetUpInformationUnit> draftPool, SimplifiedWindowPatternCard wp) {
        view.showCommonBoard(draftPool, wp);
    }

    @Override
    public void setCommonBoard(Map<String,SimplifiedWindowPatternCard> players, int [] idPubObj, int[] idTool){
        view.setCommonBoard(players,idPubObj, idTool);
    }

    @Override
    public void setDraft(List<SetUpInformationUnit> draft){
        view.setDraft(draft);
    }

    @Override
    public void setPlayer(String userName, int nFavTokens, int idPrivateObj){
        view.setPlayer(userName,nFavTokens,idPrivateObj);
    }

    @Override
    public void showCommand() {
        view.showCommand();
    }

    @Override
    public void giveProperObjectToFill(SetUpInformationUnit setInfoUnit) {
        view.giveProperObjectToFill(setInfoUnit);
    }

    // DA CANCELLARE
    @Override
    public void showUpdatedWp(String username, SetUpInformationUnit info) {
        view.showUpdatedWp(username, info);
    }

    @Override
    public void updateOwnWp(String userName, SetUpInformationUnit unit){
        view.updateOwnWp(userName, unit);
    }

    @Override
    public void updateAllWp(Map<String, SetUpInformationUnit> allWp){
        view.updateAllWp(allWp);
    }

    @Override
    public void updateDraft(InformationUnit info){
        view.updateDraft(info);
    }

    @Override
    public void updateFavTokenPlayer(String userName, int nFavorToken){
        view.updateFavTokenPlayer(userName, nFavorToken);
    }

    @Override
    public void showNotice(String notice){ view.showNotice(notice);}


    @Override
    public void setMyTurn(boolean myTurn){ view.setMyTurn(myTurn);}

}
