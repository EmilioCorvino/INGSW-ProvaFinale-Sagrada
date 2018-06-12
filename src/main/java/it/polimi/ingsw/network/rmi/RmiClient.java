package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplified_view.InformationUnit;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.view.ClientImplementation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

/**
 * This class is the implementation of the remote interface {@link IRmiClient} and it's used for callback purposes.
 */
public class RmiClient extends UnicastRemoteObject implements IRmiClient {

    /**
     * Effective instance of the client. It can call methods from {@link it.polimi.ingsw.view.AViewMaster}.
     */
    private transient ClientImplementation client;

    RmiClient(ClientImplementation client) throws RemoteException{
        this.client = client;
    }

    /**
     * Shows the waiting room to the player owning the client.
     * @param players names of the players already connected (including the player itself).
     */
    @Override
    public void showRoom(List<String> players) {
        this.client.showRoom(players);
    }

    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) {
        this.client.showMapsToChoose(listWp);
    }

    @Override
    public void choseWpId() {
        this.client.choseWpId();
    }

    @Override
    public void showPrivateObjective(int privateObjCardId) {
        this.client.showPrivateObjective(privateObjCardId);
    }

    @Override
    public void setCommonBoard(Map<String, SimplifiedWindowPatternCard> players, int[] idPubObj, int[] idTool) {
        this.client.setCommonBoard(players, idPubObj, idTool);
    }

    @Override
    public void setDraft(List<SetUpInformationUnit> draft) {
        this.client.setDraft(draft);
    }

    @Override
    public void setFavorToken(int nFavTokens) {
        this.client.setFavorToken(nFavTokens);
    }

    @Override
    public void showCommand(List<Commands> commands) {
        this.client.showCommand(commands);
    }

    @Override
    public void showUpdatedWp(String username, SetUpInformationUnit info) {
        this.client.showUpdatedWp(username, info);
    }

    @Override
    public void updateOwnWp(SetUpInformationUnit unit) {
        this.client.addOnOwnWp(unit);
    }

    @Override
    public void updateOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) {
        this.client.addOnOtherPlayerWp(userName, infoUnit);
    }

    @Override
    public void updateDraft(InformationUnit info) {
        this.client.removeOnDraft(info);
    }

    @Override
    public void updateFavTokenPlayer(int nFavorToken) {
        this.client.updateFavTokenPlayer(nFavorToken);
    }

    @Override
    public void showNotice(String notice) {
        this.client.showNotice(notice);
    }

}
