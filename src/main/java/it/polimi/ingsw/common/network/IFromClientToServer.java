package it.polimi.ingsw.common.network;

import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.common.utils.exceptions.MatchAlreadyStartedException;
import it.polimi.ingsw.common.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.common.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.server.model.player.Player;

import java.util.List;

/**
 * This interface lists all the methods the client can require from the server. Methods in this interface are agnostic
 * towards the protocol used for networking. They can be used both for RMI and Socket.
 * @see it.polimi.ingsw.common.network.rmi.RmiFromClientToServer
 */
public interface IFromClientToServer {

    /**
     * Lets the player log in the match.
     * @param gameMode can be either single-player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     * @throws UserNameAlreadyTakenException when a user with the same username is already logged in.
     * @throws TooManyUsersException when there already is the maximum number of players inside a game.
     * @throws MatchAlreadyStartedException when the match is already started at the moment the request arrives.
     * @throws BrokenConnectionException when the connection drops.
     */
    void login(int gameMode, String playerName) throws UserNameAlreadyTakenException,
            TooManyUsersException, MatchAlreadyStartedException, BrokenConnectionException;

    /**
     * Lets the player choose of the {@link it.polimi.ingsw.server.model.die.containers.WindowPatternCard}.
     * @param idMap id of the chosen window.
     * @throws BrokenConnectionException when the connection drops.
     */
    void windowPatternCardRequest(int idMap) throws BrokenConnectionException;

    /**
     * Lets the player perform a standard die placement.
     * @param infoUnit object containing all the needed information to make the move.
     * @throws BrokenConnectionException when the connection drops.
     */
    void performDefaultMove(SetUpInformationUnit infoUnit) throws BrokenConnectionException;

    /**
     * Lets the player perform a move related to a particular {@link it.polimi.ingsw.server.model.cards.tool.ToolCard}.
     * @param slotID ID of the slot the Tool Card is in.
     * @param infoUnits list of {@link SetUpInformationUnit}s needed to perform the move.
     * @throws BrokenConnectionException when the connection drops.
     */
    void performToolCardMove(int slotID, List<SetUpInformationUnit> infoUnits) throws BrokenConnectionException;

    /**
     * Lets the player place a specific die (the one shown with
     * {@link it.polimi.ingsw.server.controller.managers.GamePlayManager#showDraftedDie(Player, SetUpInformationUnit)}) onto
     * his {@link it.polimi.ingsw.server.model.die.containers.WindowPatternCard}.
     * @param infoUnit object containing all the needed information to perform the placement.
     * @throws BrokenConnectionException when the connection drops.
     */
    void performRestrictedPlacement(SetUpInformationUnit infoUnit) throws BrokenConnectionException;

    /**
     * Lets the player end his turn.
     * @throws BrokenConnectionException when the connection drops.
     */
    void moveToNextTurn() throws BrokenConnectionException;

    /**
     * Lets the player start a new match after the one he was in just finished.
     * @throws BrokenConnectionException when the connection drops.
     */
    void startNewGameRequest() throws BrokenConnectionException;

    /**
     * Lets the player log out from the game.
     * @throws BrokenConnectionException when the connection drops.
     */
    void exitGame() throws BrokenConnectionException;

    /**
     * This method is used to reconnect the player during the match.
     * @throws BrokenConnectionException when the connection drops.
     */
    void reconnect() throws BrokenConnectionException;

}
