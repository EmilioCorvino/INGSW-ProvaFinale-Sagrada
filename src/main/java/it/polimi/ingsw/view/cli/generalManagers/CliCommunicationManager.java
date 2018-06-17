package it.polimi.ingsw.view.cli.generalManagers;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.ICommunicationManager;
import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;

import java.util.logging.Level;

/**
 * This class contains all the methods to run all the possible commands in the game.
 */
public class CliCommunicationManager implements ICommunicationManager {

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


    public CliCommunicationManager(CliView view){
        this.view = view;
        this.inputOutputManager = view.getInputOutputManager();
        this.server = view.getServer();
    }

    @Override
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

    @Override
    public void showAllWp(){
        view.getSetUpManager().printAllWp(view.getCommonBoard().getPlayers());
    }

    @Override
    public void showPublicObj(){
        view.getSetUpManager().printPubObj(view.getCommonBoard().getPublicObjectiveCards());
    }

    @Override
    public void showTool(){
        view.getSetUpManager().printTool(view.getCommonBoard().getToolCardViews());
    }

    @Override
    public void showPrivateObj(){
        view.getSetUpManager().printPrivateObj(view.getPlayer().getPrivateObjCard());
    }

    @Override
    public void chooseWp() {
        this.view.choseWpId();
    }

    @Override
    public void printCommands(){
        view.printCommands();
    }

    @Override
    public void moveToNextTurn(){
        try {
            server.moveToNextTurn();
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken while moving to next turn", e);
        }
    }

    @Override
    public void exitGame(){
        try{
            server.exitGame();
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during log out", e);
        }
        view.getScannerThread().stopExecution();
        inputOutputManager.print("\nDISCONNESSIONE AVVENUTA CON SUCCESSO");
        System.exit(0);
    }

}
