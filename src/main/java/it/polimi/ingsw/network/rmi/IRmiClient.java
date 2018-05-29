package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.die.diecontainers.DiceDraftPool;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This interface presents the same methods as {@link it.polimi.ingsw.network.IFromServerToClient}, but suited for a RMI
 * connection: in fact it extends remote and its methods throw {@link RemoteException}.
 */
public interface IRmiClient extends Remote {

    /**
     * Shows the waiting room to the player owning the client.
     * @param players names of the players already connected (including the player itself).
     */
    void showRoom(List<String> players) throws RemoteException;

    void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) throws RemoteException;

    void showCommonBoard(DiceDraftPool draft, SimplifiedWindowPatternCard wp) throws RemoteException;

    void showCommand() throws RemoteException;

    void giveProperObjectToFill(SetUpInformationUnit setInfoUnit) throws RemoteException;

    void showUpdatedWp(String username, SetUpInformationUnit info) throws RemoteException;

}
