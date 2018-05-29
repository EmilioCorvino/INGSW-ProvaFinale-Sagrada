package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.die.diecontainers.DiceDraftPool;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;

import java.util.List;

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

    void showCommonBoard(DiceDraftPool draft, SimplifiedWindowPatternCard wp) throws BrokenConnectionException;

    void giveProperObjectToFill(SetUpInformationUnit setInfoUnit) throws BrokenConnectionException;

}
