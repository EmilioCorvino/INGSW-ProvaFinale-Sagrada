package it.polimi.ingsw.view.gui.gamewindows;

import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.IDefaultMatchManager;
import it.polimi.ingsw.view.gui.GUIView;
import it.polimi.ingsw.view.gui.PlayersData;

import java.util.logging.Level;

public class GUIDefaultMatchManager implements IDefaultMatchManager {

    private IFromClientToServer server;

    private PlayersData playersData;

    private GUIView view;

    public GUIDefaultMatchManager(GUIView view) {
        this.view = view;
    }

    @Override
    public void chooseWp() {
        try {
            this.server.windowPatternCardRequest(Integer.parseInt(playersData.getPersonalWp().getIdMap().getText()));
        } catch (BrokenConnectionException br) {
            SagradaLogger.log(Level.SEVERE, "Connection broken during map id choose.");
        }
    }

    @Override
    public void defaultPlacement() {

    }

    @Override
    public void showAllWp() {

    }

    @Override
    public void showPublicObj() {

    }

    @Override
    public void showTool() {

    }

    @Override
    public void showPrivateObj() {

    }

    @Override
    public void showRoundTrack() {

    }

    @Override
    public void printCommands() {

    }

    @Override
    public void moveToNextTurn() {

    }

    @Override
    public void exitGame() {

    }

    @Override
    public void newGame() {

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
