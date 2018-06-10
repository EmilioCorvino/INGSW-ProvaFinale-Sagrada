package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplified_view.InformationUnit;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;

import java.util.List;
import java.util.Map;

/**
 * This interface lists all the methods the server can require from the client. Methods in this interface are agnostic
 * towards the protocol used for networking. They can be used both for RMI and Socket.
 * @see it.polimi.ingsw.network.rmi.RmiFromServerToClient
 * @see it.polimi.ingsw.network.socket.SocketFromServerToClient
 */
public interface IFromServerToClient {

    /**
     * Shows the waiting room to the player owning the client.
     * @param players names of the players already connected (including the player itself).
     */
    void showRoom(List<String> players) throws BrokenConnectionException;

    void showPrivateObjective(int idPrivateObj) throws BrokenConnectionException;

    void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) throws BrokenConnectionException;

    void choseWpId() throws BrokenConnectionException;

    void setCommonBoard(Map<String,SimplifiedWindowPatternCard> players, int [] idPubObj, int[] idTool) throws BrokenConnectionException;

    void setDraft(List<SetUpInformationUnit> draft) throws BrokenConnectionException;

    void setFavorToken(int nFavTokens) throws BrokenConnectionException;

    void showCommand(List<Commands> commands) throws BrokenConnectionException;

    // DA CANCELLARE
    void showUpdatedWp(String username, SetUpInformationUnit info) throws BrokenConnectionException;

    void updateOwnWp(SetUpInformationUnit unit)throws BrokenConnectionException;

    void updateOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws BrokenConnectionException;

    void updateDraft(InformationUnit info) throws BrokenConnectionException;

    void updateFavTokenPlayer(int nFavorToken) throws BrokenConnectionException;

    void showNotice(String notice) throws BrokenConnectionException;

    void setMyTurn(boolean myTurn) throws BrokenConnectionException;
}
