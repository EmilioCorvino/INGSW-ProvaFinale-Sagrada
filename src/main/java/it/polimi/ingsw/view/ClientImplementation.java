package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Commands;
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
    public void showPrivateObjective(int idPrivateObj){view.showPrivateObjective(idPrivateObj);}

    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) {
        view.showMapsToChoose(listWp);
    }

    @Override
    public void choseWpId(){
        view.choseWpId();}

    @Override
    public void setCommonBoard(Map<String,SimplifiedWindowPatternCard> players, int [] idPubObj, int[] idTool){
        view.setCommonBoard(players,idPubObj, idTool);
    }

    @Override
    public void setDraft(List<SetUpInformationUnit> draft){
        view.setDraft(draft);
    }

    @Override
    public void setFavorToken(int nFavTokens){
        view.setFavorToken(nFavTokens);
    }

    @Override
    public void showCommand(List<Commands> commands) {
        view.showCommand(commands);
    }


    // DA CANCELLARE
    @Override
    public void showUpdatedWp(String username, SetUpInformationUnit info) {
        view.showUpdatedWp(username, info);
    }

    @Override
    public void updateOwnWp(SetUpInformationUnit unit){
        view.updateOwnWp(unit);
    }

    @Override
    public void updateOtherPlayerWp(String userName, SetUpInformationUnit infoUnit){
        view.updateOtherPlayerWp(userName,infoUnit);
    }

    @Override
    public void updateDraft(InformationUnit info){
        view.updateDraft(info);
    }

    @Override
    public void updateFavTokenPlayer(int nFavorToken){
        view.updateFavTokenPlayer(nFavorToken);
    }

    @Override
    public void showNotice(String notice){ view.showNotice(notice);}

}
