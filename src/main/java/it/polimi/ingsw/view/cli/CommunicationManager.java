package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;

import java.util.logging.Level;

/**
 * This class contains all the methods to run all the possible commands in the game.
 */
class CommunicationManager {

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


    CommunicationManager(CliView view){
        this.view = view;
        this.inputOutputManager = view.getInputOutputManager();
        this.server = view.getServer();
    }


    void defaultPlacement(){
        SetUpInformationUnit setInfoUnit = new SetUpInformationUnit();
        WindowPatternCardView wp = this.view.getPlayer().getWp();

        view.getGamePlayManager().getPlacementInfo(view.getCommonBoard().getDraftPool(),wp, setInfoUnit);

        try {
            server.performMove(setInfoUnit);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during normal placement",e);
        }
    }

    void showAllWp(){
        view.getSetUpManager().printAllWp(view.getCommonBoard().getPlayers());
    }

    void showPublicObj(){
        view.getSetUpManager().printPubObj(view.getCommonBoard().getPublicObjectiveCards());
    }

    void showTool(){
        view.getSetUpManager().printTool(view.getCommonBoard().getToolCards());
    }

    void showPrivateObj(){
        view.getSetUpManager().printPrivateObj(view.getPlayer().getPrivateObjCard());
    }

    void chooseWp() {
        this.view.choseWpId();
    }

}
