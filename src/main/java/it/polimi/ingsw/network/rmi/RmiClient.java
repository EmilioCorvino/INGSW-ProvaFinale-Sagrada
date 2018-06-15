package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.view.ClientImplementation;
import it.polimi.ingsw.view.IViewMaster;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

/**
 * This class is the implementation of the remote interface {@link IRmiClient} and it's used for callback purposes.
 * For documentation:
 * @see it.polimi.ingsw.network.IFromServerToClient
 * @see ClientImplementation
 */
public class RmiClient extends UnicastRemoteObject implements IRmiClient {

    /**
     * Effective instance of the client. It can call methods from {@link IViewMaster}.
     */
    private transient ClientImplementation client;

    RmiClient(ClientImplementation client) throws RemoteException{
        this.client = client;
    }

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
    public void addOnOwnWp(SetUpInformationUnit unit) {
        this.client.addOnOwnWp(unit);
    }

    @Override
    public void removeOnOwnWp(SetUpInformationUnit unit) {
        this.client.removeOnOwnWp(unit);
    }

    @Override
    public void addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) {
        this.client.addOnOtherPlayerWp(userName, infoUnit);
    }

    @Override
    public void removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) {
        this.client.removeOnOtherPlayerWp(userName, infoUnit);
    }

    @Override
    public void addOnDraft(SetUpInformationUnit info) {
        this.addOnDraft(info);
    }

    @Override
    public void removeOnDraft(SetUpInformationUnit info) {
        this.client.removeOnDraft(info);
    }

    @Override
    public void addOnRoundTrack(SetUpInformationUnit info) {
        this.client.addOnRoundTrack(info);
    }

    @Override
    public void removeOnRoundTrack(SetUpInformationUnit info) {
        this.client.removeOnRoundTrack(info);
    }

    @Override
    public void updateFavTokenPlayer(int nFavorToken) {
        this.client.updateFavTokenPlayer(nFavorToken);
    }

    @Override
    public void updateToolCost(int idSlot, int cost) {
        this.client.updateToolCost(idSlot, cost);
    }

    @Override
    public void showNotice(String notice) {
        this.client.showNotice(notice);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
