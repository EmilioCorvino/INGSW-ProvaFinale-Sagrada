package it.polimi.ingsw.network;

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

    /**
     *
     * @param map1
     * @param map2
     * @param map3
     * @param map4
     */
   // void windowPatternCardSelection(String map1, String map2, String map3, String map4) throws BrokenConnectionException;

    /**
     *
     * @param listWp
     */
    void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) throws BrokenConnectionException;

    void choseWpId() throws BrokenConnectionException;

    // DA CANCELLARE
    void showCommonBoard(List<SetUpInformationUnit> draftPool, SimplifiedWindowPatternCard wp) throws BrokenConnectionException;

    void setCommonBoard(Map<String,SimplifiedWindowPatternCard> players, int [] idPubObj, int[] idTool) throws BrokenConnectionException;

    void setDraft(List<SetUpInformationUnit> draft) throws BrokenConnectionException;

    void setPlayer(String userName, int nFavTokens, int idPrivateObj) throws BrokenConnectionException;

    void showCommand() throws BrokenConnectionException;

    void giveProperObjectToFill(SetUpInformationUnit setInfoUnit) throws BrokenConnectionException;

    // DA CANCELLARE
    void showUpdatedWp(String username, SetUpInformationUnit info) throws BrokenConnectionException;

    void updateOwnWp(String userName, SetUpInformationUnit unit)throws BrokenConnectionException;

    void updateAllWp(Map<String, SetUpInformationUnit> allWp) throws BrokenConnectionException;

    void updateDraft(InformationUnit info) throws BrokenConnectionException;

    void updateFavTokenPlayer(String userName, int nFavorToken) throws BrokenConnectionException;

    void showNotice(String notice) throws BrokenConnectionException;
}
