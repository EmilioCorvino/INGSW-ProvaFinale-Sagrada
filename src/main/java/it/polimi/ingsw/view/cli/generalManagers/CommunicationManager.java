package it.polimi.ingsw.view.cli.generalManagers;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;

import java.util.logging.Level;

/**
 * This class contains all the methods to run all the possible commands in the game.
 */
public class CommunicationManager {

    /**
     * A reference to the view.
     */
    private CliView view;

    /**
     * A reference to the inputOutputManager of the view
     */
    private InputOutputManager inputOutputManager;

    /**
     * The network interface for the connection
     */
    private IFromClientToServer server;


    public CommunicationManager(CliView view){
        this.view = view;
        this.inputOutputManager = view.getInputOutputManager();
        this.server = view.getServer();
    }


    public void defaultPlacement(){
        SetUpInformationUnit setInfoUnit = new SetUpInformationUnit();
        WindowPatternCardView wp = this.view.getPlayer().getWp();

        view.getGamePlayManager().getPlacementInfo(view.getCommonBoard().getDraftPool(),wp, setInfoUnit);

        try {
            server.performDefaultMove(setInfoUnit);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during normal placement",e);
        }
    }

    public void showAllWp(){
        view.getSetUpManager().printAllWp(view.getCommonBoard().getPlayers());
    }

    public void showPublicObj(){
        view.getSetUpManager().printPubObj(view.getCommonBoard().getPublicObjectiveCards());
    }

    public void showTool(){
        view.getSetUpManager().printTool(view.getCommonBoard().getToolCards());
    }

    public void showPrivateObj(){
        view.getSetUpManager().printPrivateObj(view.getPlayer().getPrivateObjCard());
    }

    public void chooseWp() {
        this.view.choseWpId();
    }

    public void printCommands(){
        view.printCommands();
    }

    public void moveToNextTurn(){
        try {
            server.moveToNextTurn();
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken while moving to next turn", e);
        }
    }

    public void exitGame(){
        try{
            server.exitGame(view.getPlayer().getUserName());
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during log out", e);
        }
        view.getScannerThread().stopExecution();
        inputOutputManager.print("\nDISCONNESSIONE AVVENUTA CON SUCCESSO");
        System.exit(0);
    }

}
