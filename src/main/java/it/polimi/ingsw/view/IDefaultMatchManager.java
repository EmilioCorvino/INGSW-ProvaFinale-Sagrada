package it.polimi.ingsw.view;

public interface IDefaultMatchManager {

    /**
     * This method is used to chose the wp during the game set up.
     */
    void chooseWp();

    /**
     * This method is used to collect all the information needed for a default placement, and send them to the server.
     */
    void defaultPlacement();

    /**
     * This method show the wp of the other player.
     */
    void showAllWp();

    /**
     * This method show the public objective card.
     */
    void showPublicObj();

    /**
     * This method show the tool card in the common board.
     */
    void showTool();

    /**
     * This method show the private objective of the player connected.
     */
    void showPrivateObj();

    /**
     * This method show the round track to the user.
     */
    void showRoundTrack();

    /**
     * This method print the description of the command insert.
     */
    void printDescription();

    /**
     * This method show the command available
     */
    void printCommands();

    /**
     * This method is use to tell the server that the user want to move to the next turn
     */
    void moveToNextTurn();

    /**
     * This method notify the disconnection to the server and then close the application.
     */
    void exitGame();

    /**
     * This method is used to start a new game after an other one.
     */
    void newGame();

    /**
     * This method is used to reconnect the player during the match.
     */
    void reconnect();

}
