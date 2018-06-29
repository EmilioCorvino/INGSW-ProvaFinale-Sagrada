
package it.polimi.ingsw.view.cli.generalmanagers;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.IDefaultMatchManager;
import it.polimi.ingsw.view.cli.CliCommunicationManager;
import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.cli.commands.UserCommands;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;

import java.util.logging.Level;

/**
 * This class contains all the methods to run all the possible commands in the game.
 */

public class CliDefaultMatchManager extends CliCommunicationManager implements IDefaultMatchManager {

    public CliDefaultMatchManager(CliView view){
        super(view);
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
        }
    }

    @Override
    public void showAllWp(){
        super.inputOutputManager.print(super.view.getCommonBoard().allWpToString());
    }

    @Override
    public void showPublicObj(){
        super.inputOutputManager.print(super.view.getCommonBoard().pubObjToString());
    }

    @Override
    public void showTool(){
        super.inputOutputManager.print(super.view.getCommonBoard().toolCardToString());
    }

    @Override
    public void showPrivateObj(){
        super.inputOutputManager.print(super.view.getPlayer().privateObjToString());
    }

    @Override
    public void showRoundTrack(){
        super.inputOutputManager.print(super.view.getCommonBoard().getRoundTrack().roundTrackToString());
    }

    @Override
    public void chooseWp() {
        super.view.getFunctions().remove(UserCommands.SCELTA_WP.getName());
        try {
            server.windowPatternCardRequest(super.view.getSetUpManager().getIdChosen());
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during map id choose.");
        }
    }

    @Override
    public void printDescription(){
        String command = inputOutputManager.askInformation("Inserisci il comando di cui si richiede la descrizione: ");
        String filteredString = super.view.stringConverter(command);

        if(view.getFunctions().keySet().contains(filteredString)) {
            for (UserCommands c : UserCommands.values())
                if (c.getName().equals(filteredString))
                    inputOutputManager.print(c.getDescription());
        }else
            inputOutputManager.print("Comando non disponibile");
    }

    @Override
    public void printCommands(){
        super.view.printCommands();
    }

    @Override
    public void moveToNextTurn(){
        try {
            super.server.moveToNextTurn();
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken while moving to next turn", e);
        }
    }

    @Override
    public void exitGame(){
        try{
            super.server.exitGame();
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during log out", e);
        }
        super.view.getScannerThread().stopExecution();
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
        }
    }

    private void resetClient(){
        super.view.getPlayer().resetPlayer();
        super.view.getCommonBoard().resetCommonBoard();
    }

}

