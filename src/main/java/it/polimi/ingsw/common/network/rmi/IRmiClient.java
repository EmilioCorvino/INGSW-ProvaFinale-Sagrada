package it.polimi.ingsw.common.network.rmi;

import it.polimi.ingsw.common.Commands;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.simplifiedview.SimplifiedWindowPatternCard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * This interface presents the same methods as {@link it.polimi.ingsw.common.network.IFromServerToClient}, but suited for a RMI
 * connection: in fact it extends remote and its methods throw {@link RemoteException}.
 * @see it.polimi.ingsw.common.network.IFromServerToClient for documentation.
 */
public interface IRmiClient extends Remote {

    void showRoom(List<String> players) throws RemoteException;

    void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) throws RemoteException;

    void showPrivateObjective(int privateObjCardId) throws RemoteException;

    void setCommonBoard(Map<String,SimplifiedWindowPatternCard> players, int [] idPubObj, int[] idTool) throws RemoteException;

    void setDraft(List<SetUpInformationUnit> draft) throws RemoteException;

    void setFavorToken(int nFavTokens) throws RemoteException;

    void setRestoredWindowPatternCards(Map<String, List<SetUpInformationUnit>> diceToRestore) throws RemoteException;

    void showCommand(List<Commands> commands) throws RemoteException;

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

    void showDie(SetUpInformationUnit informationUnit) throws RemoteException;

    void showRank(String[] playerNames, int[] scores) throws RemoteException;

    void showNotice(String notice) throws RemoteException;

    void forceLogOut() throws RemoteException;
}
