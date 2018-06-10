package it.polimi.ingsw.network.rmi;

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
    public void showCommonBoard(List<SetUpInformationUnit> draftPool, SimplifiedWindowPatternCard wp) {
        this.client.showCommonBoard(draftPool, wp);
    }

    @Override
    public void showPrivateObjectiveCard(int privateObjCardId) {
        this.client.showPrivateObjectiveCard(privateObjCardId);
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
    public void setFavorTokens(int nFavTokens) {
        this.client.setFavorTokens(nFavTokens);
    }

    @Override
    public void showCommand() {
        this.client.showCommand();
    }

    @Override
    public void giveProperObjectToFill(SetUpInformationUnit setInfoUnit) {
        this.client.giveProperObjectToFill(setInfoUnit);
    }

    @Override
    public void showUpdatedWp(String username, SetUpInformationUnit info) {
        this.client.showUpdatedWp(username, info);
    }

    @Override
    public void updateOwnWp(SetUpInformationUnit unit) {
        this.client.updateOwnWp(unit);
    }

    @Override
    public void updateOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) {
        this.client.updateOtherPlayerWp(userName, infoUnit);
    }

    @Override
    public void updateDraft(InformationUnit info) {
        this.client.updateDraft(info);
    }

    @Override
    public void updateFavTokenPlayer(int nFavorToken) {
        this.client.updateFavTokenPlayer(nFavorToken);
    }

    @Override
    public void showNotice(String notice) {
        this.client.showNotice(notice);
    }

    @Override
    public void setMyTurn(boolean myTurn) {
        this.client.setMyTurn(myTurn);
    }
}
