package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.view.ClientImplementation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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
    public void showNotice(String notice) {
        this.client.showNotice(notice);
    }
}
