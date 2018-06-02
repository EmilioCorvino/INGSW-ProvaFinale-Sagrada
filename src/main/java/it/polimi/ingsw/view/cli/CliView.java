package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.die.CommonBoardView;
import it.polimi.ingsw.view.cli.die.DieDraftPoolView;
import it.polimi.ingsw.view.cli.die.PlayerView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;
import it.polimi.ingsw.view.cli.stateManagers.EndGameCli;
import it.polimi.ingsw.view.cli.stateManagers.GamePlayCli;
import it.polimi.ingsw.view.cli.stateManagers.LoginCli;
import it.polimi.ingsw.view.cli.stateManagers.SetUpGameCli;

import java.util.List;
import java.util.logging.Level;

public class CliView extends AViewMaster{

    /**
     * The user name of the player connected with the client.
     */
    private PlayerView player;

    /**
     * The structure that contain the draft pool the cards (public objective and tool).
     */
    private CommonBoardView commonBoard;

    /**
     * Input Output Manager.
     */
    private InputOutputManager inputOutputManager;

    /**
     * The network interface for the connection
     */
    private IFromClientToServer server;

    /**
     *The manager of the connection and login state
     */
    private LoginCli loginState;

    /**
     * The manager of the game set up.
     */
    private SetUpGameCli initializationState;

    /**
     * The manager of the game play.
     */
    private GamePlayCli gamePlaySate;

    /**
     * The manager of the game play.
     */
    private EndGameCli endGameState;

    public CliView(){
        player = new PlayerView();
        commonBoard = new CommonBoardView();
        inputOutputManager = new InputOutputManager();
        loginState = new LoginCli();
        initializationState = new SetUpGameCli();
        gamePlaySate = new GamePlayCli();
        endGameState = new EndGameCli();
    }

//----------------------------------------------------------
//                      LOGIN STATE
//----------------------------------------------------------
    /**
     * This method create the connection and logs the player
     * @param viewMaster Is the view connected to the net.
     */
    @Override
    public void createConnection(AViewMaster viewMaster) {
        boolean userNameOk = false;
        boolean ipOk = false;

        while(!ipOk){
            try{
                String ipAddress = loginState.getIp();
                this.server = loginState.chooseNetworkInterface(ipAddress, viewMaster);
                ipOk = true;
            }catch (BrokenConnectionException e){
                inputOutputManager.print("Indirizzo IP non corretto.");
            }
        }

        inputOutputManager.print("\nConnessione stabilita.\nProcedere con il login.");

        while(!userNameOk){
            try {
                this.player.setUserName(loginState.getUsername());
                this.server.login(loginState.getGameMode(), player.getUserName());
                userNameOk = true;
            } catch (UserNameAlreadyTakenException e) {
                inputOutputManager.print("Username già in uso!");
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Connection broken during login", e);
            } catch (TooManyUsersException e) {
                inputOutputManager.print("Partita piena, numero massimo di giocatori raggiunto\nArrivederci.");
            }
        }
    }

    /**
     *This method print the name of all the players connected.
     * @param players: list of username of players connected.
     */
    @Override
    public void showRoom(List<String> players) {
        this.loginState.showRoom(players);
    }


//----------------------------------------------------------
//                  INITIALIZATION STATE
//----------------------------------------------------------
    /**
     *This method print for maps to the user and return to the server the id of the map chosen colling windowPatternRequest().
     * @param listWp: The list of maps need to be choose.
     */
    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) {
        try {
            this.server.windowPatternCardRequest(this.initializationState.showMapsToChoose(listWp));
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken during map choosing", e);
        }
    }

    /**
     *This method take the info from the server to initialize the common board at the beginning of the match.
     * @param draftPool
     * @param wp :
     */
    @Override
    public void showCommonBoard(List<SetUpInformationUnit> draftPool, SimplifiedWindowPatternCard wp){
        this.player.setWp(new WindowPatternCardView(wp));
        DieDraftPoolView draft = new DieDraftPoolView(draftPool);

        this.commonBoard.setDraftPool(draft);
        this.commonBoard.getPlayers().add(player);
        initializationState.showCommonBoard(draft, player.getWp());
    }

//----------------------------------------------------------
//                  GAME PLAY STATE
//----------------------------------------------------------
    /**
     *This method show the available commands to the user and allow him to chose one.
     */
    @Override
    public void showCommand() {
        int command = gamePlaySate.showCommands();
        switch (command){
            case 1:     try{
                            server.defaultMoveRequest();
                        } catch (BrokenConnectionException e){
                            SagradaLogger.log(Level.SEVERE, "Connection broken during placement move",e);
                        }
                        break;

            case 2:     try{
                            server.toolMoveRequest();
                        } catch (BrokenConnectionException e){
                            SagradaLogger.log(Level.SEVERE, "Connection broken during tool move",e);
                        }
                        break;

            case 3:     gamePlaySate.printAllWp();

            case 4:     gamePlaySate.printCard(commonBoard.getPublicObjectiveCards(), "obiettivo pubblico");
                        gamePlaySate.showCommands();
                        break;

            case 5:     gamePlaySate.printCard(commonBoard.getToolCards(), "strumento");
                        gamePlaySate.showCommands();
                        break;

            case 6:     try{
                            server.endTurn();
                        } catch (BrokenConnectionException e) {
                            SagradaLogger.log(Level.SEVERE, "Connection broken during end game", e);
                        }
                        break;

        }
    }

    /**
     * This method fill the information unit with all the input token from the user.
     * @param setInfoUnit: This object contains all of the placement info chosen by the user.
     */
    @Override
    public void giveProperObjectToFill(SetUpInformationUnit setInfoUnit) {
        gamePlaySate.getPlacementInfo(commonBoard.getDraftPool(), player.getWp(), setInfoUnit);

        try {
            server.performMove(setInfoUnit);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during normal placement",e);
        }
    }

    /**
     * This method update the wp of a specified player.
     * @param username: Username of player that need the update.
     * @param unit: The set of information needed to place a die in the wp.
     */
    @Override
    public void showUpdatedWp(String username, SetUpInformationUnit unit) {

        for (PlayerView p : commonBoard.getPlayers())
            if(p.getUserName().equals(username))
                gamePlaySate.updateWp(p.getWp(), unit);


        if (player.getUserName().equals(username)) {
            inputOutputManager.print("Dado piazzato!");
            player.getWp().printWp();
        }
    }

    @Override
    public void showNotice(String notice){
        inputOutputManager.print(notice);
    }

//----------------------------------------------------------
//                  END GAME STATE
//----------------------------------------------------------




//----------------------------------------------------------
//                  GENERAL METHODS
//----------------------------------------------------------
    public IFromClientToServer getServer() {
        return server;
    }

    public void setServer(IFromClientToServer server) {
        this.server = server;
    }
}
