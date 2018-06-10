package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.die.PlayerView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;

import java.util.List;
import java.util.Map;
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
        WindowPatternCardView wp = this.view.getPlayerConnectedFromCommonBoard().getWp();

        view.getGamePlaySate().getPlacementInfo(view.getCommonBoard().getDraftPool(),wp, setInfoUnit);

        try {
            server.performMove(setInfoUnit);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during normal placement",e);
        }
    }

    void showAllWp(){
        this.printAllWp(view.getCommonBoard().getPlayers(), view.getPlayer());
    }

    void showPublicObj(){
        this.printPubObj(view.getCommonBoard().getPublicObjectiveCards());
    }

    void showTool(){
        this.printTool(view.getCommonBoard().getToolCards());
    }

    void showPrivateObj(){
        this.printPrivateObj(view.getPlayer().getPrivateObjCard());
    }


    /**
     * This method print all the public objective.
     * @param cards: The list public objective card.
     */
    private void printPubObj(List<String> cards){
        int index = 1;

        inputOutputManager.print("\nCarte obiettivo pubblico: ");
        for (String s : cards){
            inputOutputManager.print("\t - " + index + ": " + s);
            index ++;
        }
    }

    /**
     * This method print all the tool cards.
     * @param cards: The list of Tool Cards.
     */
    private void printTool(Map<String, Integer> cards){
        int index = 1;

        inputOutputManager.print("\nCarte strumento: ");
        for (Map.Entry<String,Integer> s : cards.entrySet()){
            inputOutputManager.print("\t - " + index + ": " + s.getKey() + " | Segnalini favore da usare: " + s.getValue());
            index ++;
        }
    }

    /**
     * This method print the private objective.
     * @param card: The private objective card.
     */
    private void printPrivateObj(String card){
        inputOutputManager.print("\nIl tuo obiettivo privato e': "+ card);
    }

    /**
     * This method print all the wp of the other player in the game.
     * @param players: all the players in the match
     * @param currPlayer: the player connected to this client.
     */
    private void printAllWp(List<PlayerView> players, PlayerView currPlayer){
        for(PlayerView p : players){
            if(!p.getUserName().equals(currPlayer.getUserName())) {
                inputOutputManager.print("\nGiocatore " + p.getUserName());
                p.getWp().printWp();
            }
        }
    }
}
