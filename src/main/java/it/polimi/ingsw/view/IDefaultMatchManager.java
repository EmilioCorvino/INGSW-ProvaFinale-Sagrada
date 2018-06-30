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

    void visualization();

}
