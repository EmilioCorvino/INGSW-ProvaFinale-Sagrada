package it.polimi.ingsw.client.view.gui.gamewindows;

import it.polimi.ingsw.client.view.IDefaultMatchManager;
import it.polimi.ingsw.client.view.gui.GUIView;
import it.polimi.ingsw.client.view.gui.PlayersData;
import it.polimi.ingsw.common.network.IFromClientToServer;
import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;

import java.util.logging.Level;

/**
 * This class manages the basic operation for the Sagrada game collecting and preparing all the needed information
 * to send to the server to perform the actions of the player.
 */
public class GUIDefaultMatchManager implements IDefaultMatchManager {

    /**
     * The server where to send the information.
     */
    private IFromClientToServer server;

    /**
     * The data where to pick, collect and prepare the information.
     */
    private PlayersData playersData;

    /**
     * The GUI view of the game.
     */
    private GUIView view;

    public GUIDefaultMatchManager(GUIView view) {
        this.view = view;
    }

    /**
     * This method sends to the server the choice of the window pattern card in the setup phase of the game.
     */
    @Override
    public void chooseWp() {
        try {
            this.server.windowPatternCardRequest(Integer.parseInt(playersData.getPersonalWp().getIdMap().getText().split(" ")[1]));
        } catch (BrokenConnectionException br) {
            SagradaLogger.log(Level.SEVERE, "Connection broken during map id choose.");
        }
    }

    /**
     * This method sends to the server all the needed information to perform a single default placement.
     */
    @Override
    public void defaultPlacement() {
        try {
            this.server.performDefaultMove(playersData.getSetUpInformationUnit());
        } catch(BrokenConnectionException br) {
            SagradaLogger.log(Level.SEVERE, "Connection broken during map id choose.");
        }
    }

    /**
     * This method sends to the server the request to pass the turn of the current player by the current
     * player itself.
     */
    @Override
    public void moveToNextTurn() {
        try {
            this.server.moveToNextTurn();
        } catch (BrokenConnectionException br) {
            SagradaLogger.log(Level.SEVERE, "Connection broken while moving to next turn", br);
        }
    }

    @Override
    public void exitGame() {
        System.out.println("arriva nell exit game");
        try{
            this.server.exitGame();
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during log out");
           // disconnect();
        }
    }

    @Override
    public void newGame() {

    }

    @Override
    public void reconnect() {

    }

    @Override
    public void visualization() {

    }

    public IFromClientToServer getServer() {
        return server;
    }

    public void setServer(IFromClientToServer server) {
        this.server = server;
    }

    public PlayersData getPlayersData() {
        return playersData;
    }

    public void setPlayersData(PlayersData playersData) {
        this.playersData = playersData;
    }

    public GUIView getView() {
        return view;
    }

    public void setView(GUIView view) {
        this.view = view;
    }
}