package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplifiedview.SimplifiedWindowPatternCard;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;

import java.util.List;
import java.util.Map;

/**
 * This interface lists all the methods the server can require from the client. Methods in this interface are agnostic
 * towards the protocol used for networking. They can be used both for RMI and Socket (their respective exceptions are
 * wrapped inside {@link BrokenConnectionException}.
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
     * This method set the private objective of the player and than show it to him.
     * @param idPrivateObj: the id of the private objective drown.
     */
    void showPrivateObjective(int idPrivateObj) throws BrokenConnectionException;

    /**
     *This method prints four maps to the user and triggers the {@link IFromClientToServer#windowPatternCardRequest(int)}.
     * @param listWp: The list of maps need to be chosen.
     */
    void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) throws BrokenConnectionException;

    /**
     * This method will set the common board with all the common information for each player.
     * @param players: A map witch contains a simplified wp for each userName that identify a player.
     * @param idPubObj: The ids of all the public objective cards drown by the controller.
     * @param idTool: The ids of all the tool cards drown by the controller.
     */
    void setCommonBoard(Map<String,SimplifiedWindowPatternCard> players, int [] idPubObj, int[] idTool) throws BrokenConnectionException;

    /**
     * This method will populate the draft pool in each round
     * @param draft: the list of dice contain in the draft Pool.
     */
    void setDraft(List<SetUpInformationUnit> draft) throws BrokenConnectionException;

    /**
     * This method gives at the player connected to this client the information of his private objective card and the number of his favor tokens.
     * @param nFavTokens : the number of favor tokens.
     */
    void setFavorToken(int nFavTokens) throws BrokenConnectionException;

    /**
     * This method populates the function map when the controller give the command list
     * @param commands: the list of commands available.
     */
    void showCommand(List<Commands> commands) throws BrokenConnectionException;

    /**
     * This method updates the wp of the player connected and print it.
     * @param unit : information for the addDieToCopy, index of matrix and die that needs to be place.
     */
    void addOnOwnWp(SetUpInformationUnit unit)throws BrokenConnectionException;

    /**
     * This method removes a die from a map in a specified index.
     * @param unit: the container with all the info needed for the remove.
     */
    void removeOnOwnWp(SetUpInformationUnit unit) throws BrokenConnectionException;

    /**
     * This method updates the wp of all player.
     * @param userName : The userName of the player with the wp modified
     * @param infoUnit : The info of modification of the wp.
     */
    void addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws BrokenConnectionException;

    /**
     * This method removes the die in all the wp of all player.
     * @param userName : The userName of the player with the wp modified
     * @param infoUnit : The info of modification of the wp.
     */
    void removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws BrokenConnectionException;

    /**
     * This method adds a die from the draft in a specified index.
     * @param info : the containers of the die info.
     */
    void addOnDraft(SetUpInformationUnit info) throws BrokenConnectionException;

    /**
     * This method removes a die from the draft in a specified index.
     * @param info : the containers of the index information.
     */
    void removeOnDraft(SetUpInformationUnit info) throws BrokenConnectionException;

    /**
     * This method adds a die to the round track
     * @param info: The containers of the info.
     */
    void addOnRoundTrack(SetUpInformationUnit info) throws BrokenConnectionException;

    /**
     * This method removes a die from the round track
     * @param info: the container of the info of removing.
     */
    void removeOnRoundTrack(SetUpInformationUnit info) throws BrokenConnectionException;

    /**
     * This method updates the number of favor token assigned to a player
     * @param nFavorToken : number of favor token remain.
     */
    void updateFavTokenPlayer(int nFavorToken) throws BrokenConnectionException;

    /**
     * This method updates the cost of use for a specified tool, it is used after the first use of a tool.
     * @param idSlot: It is the id of the tool updated
     * @param cost: The new cost of the tool.
     */
    void updateToolCost(int idSlot, int cost) throws BrokenConnectionException;

    /**
     * This method show die information sent by the server after a die extraction.
     * @param informationUnit: the container of the info of the die.
     */
    void showDie(SetUpInformationUnit informationUnit) throws BrokenConnectionException;

    /**
     * This method shows to each player the rank of the match.
     * @param playerNames names of the players in the match.
     * @param scores scores achieved by each player in the match.
     */
    void showRank(String[] playerNames, int[] scores) throws BrokenConnectionException;

    /**
     * Forces the client to log out.
     */
    void forceLogOut() throws BrokenConnectionException;

    /**
     * This method sends a message to the user.
     * @param notice: the message that need to be printed.
     */
    void showNotice(String notice) throws BrokenConnectionException;

}
