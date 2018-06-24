package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.network.IFromServerToClient;

import java.util.List;
import java.util.Map;

/**
 * This class handles messages arriving from the server, calling methods from {@link IViewMaster}.
 */
public class ClientImplementation implements IFromServerToClient {

    /**
     * View chosen by the user.
     */
    private IViewMaster view;

    public ClientImplementation(IViewMaster view) {
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

    @Override
    public void addOnOwnWp(SetUpInformationUnit unit){
        view.addOnOwnWp(unit);
    }

    @Override
    public void removeOnOwnWp(SetUpInformationUnit unit){
        view.removeOnOwnWp(unit);
    }

    @Override
    public void addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit){
        view.addOnOtherPlayerWp(userName,infoUnit);
    }

    @Override
    public void removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit){
        view.removeOnOtherPlayerWp(userName, infoUnit);
    }

    @Override
    public void addOnDraft(SetUpInformationUnit info){
        view.addOnDraft(info);
    }

    @Override
    public void removeOnDraft(SetUpInformationUnit info){
        view.removeOnDraft(info);
    }

    @Override
    public void addOnRoundTrack(SetUpInformationUnit info){
        view.addOnRoundTrack(info);
    }

    @Override
    public void removeOnRoundTrack(SetUpInformationUnit info){
        view.removeOnRoundTrack(info);
    }

    @Override
    public void updateFavTokenPlayer(int nFavorToken){
        view.updateFavTokenPlayer(nFavorToken);
    }

    @Override
    public void updateToolCost(int idSlot, int cost){
        view.updateToolCost(idSlot,cost);}

    @Override
    public void showDie(SetUpInformationUnit informationUnit){
        view.showDie(informationUnit);}

    @Override
    public void showRank(String[] playerNames, int[] scores) {
        view.showRank(playerNames, scores);
    }

    @Override
    public void forceLogOut() {
        view.forceLogOut();
    }

    @Override
    public void showNotice(String notice) {
        view.showNotice(notice);
    }


}
