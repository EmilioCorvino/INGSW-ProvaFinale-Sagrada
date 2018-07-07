
package it.polimi.ingsw.client.view.cli.managers.general;

import it.polimi.ingsw.client.view.IDefaultMatchManager;
import it.polimi.ingsw.client.view.cli.CliView;
import it.polimi.ingsw.client.view.cli.commands.UserCommands;
import it.polimi.ingsw.client.view.cli.die.WindowPatternCardView;
import it.polimi.ingsw.client.view.cli.managers.CliCommunicationManager;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * This class contains all the methods to run all the possible commands in the game.
 * For the documentation of overridden methods:
 * @see IDefaultMatchManager
 */

public class CliDefaultMatchManager extends CliCommunicationManager implements IDefaultMatchManager {

    /**
     * Map containing all those commands that trigger the showing of parts of the board that the client has already
     * downloaded.
     */
    private Map<String , Runnable> visualizationCommandsMap;

    public CliDefaultMatchManager(CliView view){
        super(view);
        visualizationCommandsMap = new LinkedHashMap<>();
        populateVisualizationCommandsMap();
    }

    /**
     * Populates the {@link #visualizationCommandsMap}
     */
    private void populateVisualizationCommandsMap(){
        visualizationCommandsMap.put(UserCommands.MAPPE_ALTRI_GIOCATORI.getName(), this::showAllWp);
        visualizationCommandsMap.put(UserCommands.OBBIETTIVI_PUBBLICI.getName(), this::showPublicObj);
        visualizationCommandsMap.put(UserCommands.OBIETTIVO_PRIVATO.getName(), this::showPrivateObj);
        visualizationCommandsMap.put(UserCommands.CARTE_STRUMENTO_DISPONIBILI.getName(), this::showTool);
        visualizationCommandsMap.put(UserCommands.ROUND_TRACK.getName(), this::showRoundTrack);
    }

    @Override
    public void defaultPlacement(){
        SetUpInformationUnit setInfoUnit = new SetUpInformationUnit();
        WindowPatternCardView wp = super.view.getPlayer().getWp();

        super.view.getGamePlayManager().getPlacementInfo(super.view.getCommonBoard().getDraftPool(),wp, setInfoUnit);

        try {
            super.server.performDefaultMove(setInfoUnit);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during normal placement");
            disconnect();
        }
    }


    @Override
    public void chooseWp() {
        super.view.getFunctions().remove(UserCommands.SCELTA_WP.getName());
        try {
            server.windowPatternCardRequest(super.view.getSetUpManager().getIdChosen());
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during map id choose.");
            disconnect();
        }
    }


    @Override
    public void moveToNextTurn(){
        try {
            super.server.moveToNextTurn();
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken while moving to next turn");
            disconnect();
        }
    }

    @Override
    public void exitGame(){
        try{
            super.server.exitGame();
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during log out");
            disconnect();
        }
}

    @Override
    public void newGame(){
        this.resetClient();
        try {
            super.server.startNewGameRequest();
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during new game request.");
            disconnect();
        }
    }

    @Override
    public void reconnect() {
        try{
            super.server.reconnect();
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during reconnection");
            disconnect();
        }
    }

    @Override
    public void visualization() {
        view.getFunctions().putAll(visualizationCommandsMap);
        view.getFunctions().remove(UserCommands.VISUALIZZAZIONE.getName());
        inputOutputManager.print("Inserisci il comando corrispondente all'elemento che si vuole visualizzare.");
        view.printCommands();
    }

    /**
     * Resets the information present in the client, allowing him to start a new game.
     */
    private void resetClient(){
        super.view.getPlayer().resetPlayer();
        super.view.getCommonBoard().resetCommonBoard();
    }

    /**
     * Prints the Window Pattern Cards of the other players.
     */
    private void showAllWp(){
        super.inputOutputManager.print(super.view.getCommonBoard().allWpToString());
    }

    /**
     * Prints the Private Objective Cards present in the board.
     */
    private void showPrivateObj(){
        super.inputOutputManager.print(super.view.getPlayer().privateObjToString());
    }

    /**
     * Prints the Public Objective Cards present in the board.
     */
    private void showPublicObj(){
        super.inputOutputManager.print(super.view.getCommonBoard().pubObjToString());
    }

    /**
     * Prints the Tool Cards present in the board.
     */
    private void showTool(){
        super.inputOutputManager.print(super.view.getCommonBoard().toolCardToString());
    }

    /**
     * Prints the Round Track of the match.
     */
    private void showRoundTrack(){
        super.inputOutputManager.print(super.view.getCommonBoard().getRoundTrack().roundTrackToString());
    }

    /**
     * Prints the description of a command. It corresponds to AIUTO.
     */
    private void printDescription(){
        String command = inputOutputManager.askInformation("Inserisci il comando di cui si richiede la descrizione: ");
        String filteredString = super.view.stringConverter(command);

        if(view.getFunctions().keySet().contains(filteredString)) {
            for (UserCommands c : UserCommands.values())
                if (c.getName().equals(filteredString))
                    inputOutputManager.print(c.getDescription());
        }else
            inputOutputManager.print("Comando non disponibile");
    }

    /**
     * Prints the commands available. It corresponds to COMANDI.
     */
    private void printCommands(){
        super.view.printCommands();
    }

    /**
     * Adds to the map commands useful to help the player.
     * @param f map to which the commands have to be put.
     */
    public void populateHelp(Map<String, Runnable> f){
        f.put(UserCommands.AIUTO.getName(), this::printDescription);
        f.put(UserCommands.COMANDI.getName(), this::printCommands);
    }
}

