
package it.polimi.ingsw.view.cli.managers.general;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.utils.SagradaLogger;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.view.IDefaultMatchManager;
import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.cli.commands.UserCommands;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;
import it.polimi.ingsw.view.cli.managers.CliCommunicationManager;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * This class contains all the methods to run all the possible commands in the game.
 */

public class CliDefaultMatchManager extends CliCommunicationManager implements IDefaultMatchManager {

    private Map<String , Runnable> visualizationCommandsMap;

    public CliDefaultMatchManager(CliView view){
        super(view);
        visualizationCommandsMap = new LinkedHashMap<>();
        populateVisualizationCommandsMap();
    }

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
            SagradaLogger.log(Level.SEVERE, "Connection broken during normal placement",e);
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
            SagradaLogger.log(Level.SEVERE, "Connection broken while moving to next turn", e);
            disconnect();
        }
    }

    @Override
    public void exitGame(){
        try{
            super.server.exitGame();
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during log out", e);
            disconnect();
        }
        super.inputOutputManager.print("\nDISCONNESSIONE AVVENUTA CON SUCCESSO");
        System.exit(0);
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

    private void resetClient(){
        super.view.getPlayer().resetPlayer();
        super.view.getCommonBoard().resetCommonBoard();
    }

    private void showAllWp(){
        super.inputOutputManager.print(super.view.getCommonBoard().allWpToString());
    }

    private void showPublicObj(){
        super.inputOutputManager.print(super.view.getCommonBoard().pubObjToString());
    }

    private void showTool(){
        super.inputOutputManager.print(super.view.getCommonBoard().toolCardToString());
    }

    private void showPrivateObj(){
        super.inputOutputManager.print(super.view.getPlayer().privateObjToString());
    }

    private void showRoundTrack(){
        super.inputOutputManager.print(super.view.getCommonBoard().getRoundTrack().roundTrackToString());
    }

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

    private void printCommands(){
        super.view.printCommands();
    }

    public void populateHelp(Map<String, Runnable> f){
        f.put(UserCommands.AIUTO.getName(), this::printDescription);
        f.put(UserCommands.COMANDI.getName(), this::printCommands);
    }
}

