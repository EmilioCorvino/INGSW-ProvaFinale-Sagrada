package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.simplified_view.InformationUnit;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

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

    void choseWpId() throws RemoteException;

    void showCommonBoard(List<SetUpInformationUnit> draftPool, SimplifiedWindowPatternCard wp) throws RemoteException;

    void showPrivateObjectiveCard(int privateObjCardId) throws RemoteException;

    void setCommonBoard(Map<String,SimplifiedWindowPatternCard> players, int [] idPubObj, int[] idTool) throws RemoteException;

    void setDraft(List<SetUpInformationUnit> draft) throws RemoteException;

    void setFavorTokens(int nFavTokens) throws RemoteException;

    void showCommand() throws RemoteException;

    void giveProperObjectToFill(SetUpInformationUnit setInfoUnit) throws RemoteException;

    void showUpdatedWp(String username, SetUpInformationUnit info) throws RemoteException;

    void updateOwnWp(SetUpInformationUnit unit)throws RemoteException;

    void updateOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws RemoteException;

    void updateDraft(InformationUnit info) throws RemoteException;

    void updateFavTokenPlayer(int nFavorToken) throws RemoteException;

    void showNotice(String notice) throws RemoteException;

    void setMyTurn(boolean myTurn) throws RemoteException;

}
