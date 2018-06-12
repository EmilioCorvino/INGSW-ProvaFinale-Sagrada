package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * This interface presents the same methods as {@link it.polimi.ingsw.network.IFromServerToClient}, but suited for a RMI
 * connection: in fact it extends remote and its methods throw {@link RemoteException}.
 * @see it.polimi.ingsw.network.IFromServerToClient for documentation.
 */
public interface IRmiClient extends Remote {

    void showRoom(List<String> players) throws RemoteException;

    void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) throws RemoteException;

    void choseWpId() throws RemoteException;

    void showPrivateObjective(int privateObjCardId) throws RemoteException;

    void setCommonBoard(Map<String,SimplifiedWindowPatternCard> players, int [] idPubObj, int[] idTool) throws RemoteException;

    void setDraft(List<SetUpInformationUnit> draft) throws RemoteException;

    void setFavorToken(int nFavTokens) throws RemoteException;

    void showCommand(List<Commands> commands) throws RemoteException;

    void showUpdatedWp(String username, SetUpInformationUnit info) throws RemoteException;

    void addOnOwnWp(SetUpInformationUnit unit)throws RemoteException;

    void removeOnOwnWp(SetUpInformationUnit unit) throws RemoteException;

    void addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws RemoteException;

    void removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws RemoteException;

    void addOnDraft(SetUpInformationUnit info) throws RemoteException;

    void removeOnDraft(SetUpInformationUnit info) throws RemoteException;

    void addOnRoundTrack(SetUpInformationUnit info) throws RemoteException;

    void removeOnRoundTrack(SetUpInformationUnit info) throws RemoteException;

    void updateFavTokenPlayer(int nFavorToken) throws RemoteException;

    void updateToolCost(int idSlot, int cost) throws RemoteException;

    void showNotice(String notice) throws RemoteException;
}
