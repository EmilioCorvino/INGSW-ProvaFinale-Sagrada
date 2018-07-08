package it.polimi.ingsw.common.network;

import it.polimi.ingsw.common.Commands;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.simplifiedview.SimplifiedWindowPatternCard;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This interface lists all the methods the server can require from the client. Methods in this interface are agnostic
 * towards the protocol used for networking. They can be used both for RMI and Socket (their respective exceptions are
 * wrapped inside {@link BrokenConnectionException}.
 * @see it.polimi.ingsw.common.network.rmi.RmiFromServerToClient
 */
public interface IFromServerToClient {

    /**
     * Shows the waiting room to the player owning the client.
     * @param players names of the players already connected (including the player itself).
     * @throws BrokenConnectionException when the connection drops.
     */
    void showRoom(List<String> players) throws BrokenConnectionException;

    /**
     * This method set the private objective of the player and than show it to him.
     * @param idPrivateObj the id of the private objective drown.
     * @throws BrokenConnectionException when the connection drops.
     */
    void showPrivateObjective(int idPrivateObj) throws BrokenConnectionException;

    /**
     * This method prints four maps to the user and triggers the {@link IFromClientToServer#windowPatternCardRequest(int)}.
     * @param listWp the list of maps need to be chosen.
     * @throws BrokenConnectionException when the connection drops.
     */
    void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) throws BrokenConnectionException;

    /**
     * This method will set the common board with all the common information for each player.
     * @param players a map witch contains a simplified wp for each userName that identify a player.
     * @param idPubObj the ids of all the public objective cards drown by the controller.
     * @param idTool the ids of all the tool cards drown by the controller.
     * @throws BrokenConnectionException when the connection drops.
     */
    void setCommonBoard(Map<String, SimplifiedWindowPatternCard> players, int[] idPubObj, int[] idTool) throws BrokenConnectionException;

    /**
     * This method will populate the draft pool in each round
     * @param draft the list of dice contain in the draft Pool.
     * @throws BrokenConnectionException when the connection drops.
     */
    void setDraft(List<SetUpInformationUnit> draft) throws BrokenConnectionException;

    /**
     * This method gives at the player connected to this client the information of his private objective card and the number of his favor tokens.
     * @param nFavTokens the number of favor tokens.
     * @throws BrokenConnectionException when the connection drops.
     */
    void setFavorToken(int nFavTokens) throws BrokenConnectionException;

    /**
     * This method enables the server to add to the restored Window Pattern Cards on the View side all the dice present
     * in the Model.
     * @param diceToRestore map containing player names as keys and the list of {@link SetUpInformationUnit} representing
     *                      dice as values.
     * @throws BrokenConnectionException when the connection drops.
     */
    void setRestoredWindowPatternCards(Map<String, List<SetUpInformationUnit>> diceToRestore) throws BrokenConnectionException;

    /**
     * This method is used to restore the {@link it.polimi.ingsw.server.model.die.containers.RoundTrack} after the
     * player had previously disconnected.
     * @param roundTrackToRestore object representing the round track to restore.
     * @throws BrokenConnectionException when the connection drops.
     */
    void setRestoredRoundTrack(List<ArrayList<SetUpInformationUnit>> roundTrackToRestore) throws BrokenConnectionException;

    /**
     * This method populates the function map when the controller give the command list
     * @param commands the list of commands available.
     * @throws BrokenConnectionException when the connection drops.
     */
    void showCommand(List<Commands> commands) throws BrokenConnectionException;

    /**
     * This method updates the wp of the player connected and print it.
     * @param unit information for the addDieToCopy, index of matrix and die that needs to be place.
     * @throws BrokenConnectionException when the connection drops.
     */
    void addOnOwnWp(SetUpInformationUnit unit)throws BrokenConnectionException;

    /**
     * This method removes a die from a map in a specified index.
     * @param unit the container with all the info needed for the remove.
     * @throws BrokenConnectionException when the connection drops.
     */
    void removeOnOwnWp(SetUpInformationUnit unit) throws BrokenConnectionException;

    /**
     * This method updates the wp of all player.
     * @param userName the userName of the player with the wp modified
     * @param infoUnit the info of modification of the wp.
     * @throws BrokenConnectionException when the connection drops.
     */
    void addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws BrokenConnectionException;

    /**
     * This method removes the die in all the wp of all player.
     * @param userName the userName of the player with the wp modified
     * @param infoUnit the info of modification of the wp.
     * @throws BrokenConnectionException when the connection drops.
     */
    void removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws BrokenConnectionException;

    /**
     * This method adds a die from the draft in a specified index.
     * @param info the containers of the die info.
     * @throws BrokenConnectionException when the connection drops.
     */
    void addOnDraft(SetUpInformationUnit info) throws BrokenConnectionException;

    /**
     * This method removes a die from the draft in a specified index.
     * @param info the containers of the index information.
     * @throws BrokenConnectionException when the connection drops.
     */
    void removeOnDraft(SetUpInformationUnit info) throws BrokenConnectionException;

    /**
     * This method adds a die to the round track
     * @param info the containers of the info.
     * @throws BrokenConnectionException when the connection drops.
     */
    void addOnRoundTrack(SetUpInformationUnit info) throws BrokenConnectionException;

    /**
     * This method removes a die from the round track
     * @param info the container of the info of removing.
     * @throws BrokenConnectionException when the connection drops.
     */
    void removeOnRoundTrack(SetUpInformationUnit info) throws BrokenConnectionException;

    /**
     * This method updates the number of favor token assigned to a player
     * @param nFavorToken number of favor token remain.
     * @throws BrokenConnectionException when the connection drops.
     */
    void updateFavTokenPlayer(int nFavorToken) throws BrokenConnectionException;

    /**
     * This method updates the cost of use for a specified tool, it is used after the first use of a tool.
     * @param idSlot it is the id of the tool updated
     * @param cost the new cost of the tool.
     * @throws BrokenConnectionException when the connection drops.
     */
    void updateToolCost(int idSlot, int cost) throws BrokenConnectionException;

    /**
     * This method show die information sent by the server after a die extraction.
     * @param informationUnit the container of the info of the die.
     * @throws BrokenConnectionException when the connection drops.
     */
    void showDie(SetUpInformationUnit informationUnit) throws BrokenConnectionException;

    /**
     * This method shows to each player the rank of the match.
     * @param playerNames names of the players in the match.
     * @param scores scores achieved by each player in the match.
     * @throws BrokenConnectionException when the connection drops.
     */
    void showRank(String[] playerNames, int[] scores) throws BrokenConnectionException;

    /**
     * Forces the client to log out.
     * @throws BrokenConnectionException when the connection drops.
     */
    void forceLogOut() throws BrokenConnectionException;

    /**
     * This method sends a message to the user.
     * @param notice the message that need to be printed.
     * @throws BrokenConnectionException when the connection drops.
     */
    void showNotice(String notice) throws BrokenConnectionException;
}
