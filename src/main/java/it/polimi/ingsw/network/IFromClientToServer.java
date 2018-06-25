package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.MatchAlreadyStartedException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;

import java.util.List;

/**
 * This interface lists all the methods the client can require from the server. Methods in this interface are agnostic
 * towards the protocol used for networking. They can be used both for RMI and Socket.
 * @see it.polimi.ingsw.network.rmi.RmiFromClientToServer
 * @see it.polimi.ingsw.network.socket.SocketFromClientToServer
 */
public interface IFromClientToServer {

    /**
     * Lets the player log in the match.
     * @param gameMode can be either single-player or multi-player.
     * @param playerName name the player chooses for himself in the application.
     * @throws UserNameAlreadyTakenException when a user with the same username is already logged in.
     * @throws TooManyUsersException when there already is the maximum number of players inside a game.
     * @throws BrokenConnectionException when the connection drops.
     */
    void login(int gameMode, String playerName) throws UserNameAlreadyTakenException,
            TooManyUsersException, MatchAlreadyStartedException, BrokenConnectionException;

    /**
     * Lets the player choose of the {@link it.polimi.ingsw.model.die.containers.WindowPatternCard}.
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
     * Lets the player perform a move related to a particular {@link it.polimi.ingsw.model.cards.tool.ToolCard}.
     * @param slotID ID of the slot the Tool Card is in.
     * @param infoUnits list of {@link SetUpInformationUnit}s needed to perform the move.
     * @throws BrokenConnectionException when the connection drops.
     */
    void performToolCardMove(int slotID, List<SetUpInformationUnit> infoUnits) throws BrokenConnectionException;

    /**
     * Lets the player place a specific die (the one shown with
     * {@link it.polimi.ingsw.controller.managers.GamePlayManager#showDraftedDie(Player, SetUpInformationUnit)}) onto
     * his {@link it.polimi.ingsw.model.die.containers.WindowPatternCard}.
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

}
