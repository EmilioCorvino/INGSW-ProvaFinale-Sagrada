package it.polimi.ingsw.view.cli.generalManagers;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.cli.CliCommunicationManager;
import it.polimi.ingsw.view.IDefaultMatchManager;
import it.polimi.ingsw.view.cli.CliView;
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
        WindowPatternCardView wp = this.getView().getPlayer().getWp();

        this.getView().getGamePlayManager().getPlacementInfo(getView().getCommonBoard().getDraftPool(),wp, setInfoUnit);

        try {
            this.getServer().performDefaultMove(setInfoUnit);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during normal placement",e);
        }
    }

    @Override
    public void showAllWp(){
        this.getInputOutputManager().print(this.getView().getCommonBoard().allWpToString());
    }

    @Override
    public void showPublicObj(){
        this.getInputOutputManager().print(this.getView().getCommonBoard().pubObjToString());
    }

    @Override
    public void showTool(){
        this.getInputOutputManager().print(this.getView().getCommonBoard().toolCardToString());
    }

    @Override
    public void showPrivateObj(){
        this.getInputOutputManager().print(this.getView().getPlayer().privateObjToString());
    }

    @Override
    public void showRoundTrack(){
        this.getInputOutputManager().print(this.getView().getCommonBoard().getRoundTrack().roundTrackToString());
    }

    @Override
    public void chooseWp() {
        this.getView().choseWpId();
    }

    @Override
    public void printCommands(){
        getView().printCommands();
    }

    @Override
    public void moveToNextTurn(){
        try {
            this.getServer().moveToNextTurn();
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken while moving to next turn", e);
        }
    }

    @Override
    public void exitGame(){
        try{
            this.getServer().exitGame();
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during log out", e);
        }
        this.getView().getScannerThread().stopExecution();
        this.getInputOutputManager().print("\nDISCONNESSIONE AVVENUTA CON SUCCESSO");
        System.exit(0);
    }

    @Override
    public void newGame(){
        this.resetClient();
        /*
        try {
            this.getServer().startNewGameRequest();
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during new game request.");
        }
        */
    }

    private void resetClient(){
        this.getView().getPlayer().resetPlayer();
        this.getView().getCommonBoard().resetCommonBoard();
    }

}
