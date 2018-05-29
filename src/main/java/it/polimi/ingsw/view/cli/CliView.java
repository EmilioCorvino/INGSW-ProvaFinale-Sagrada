package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.die.diecontainers.DiceDraftPool;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.network.IFromClientToServer;
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
    private GamePlayCli gameplaySate;

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
        gameplaySate = new GamePlayCli();
        endGameState = new EndGameCli();
    }

    /**
     * This method create the connection and logs the player
     * @param viewMaster
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

        inputOutputManager.print("Connessione stabilita.\nProcedere con il login.");

        while(!userNameOk){
            try {
                this.player.setUserName(loginState.getUsername());
                this.server.login(loginState.getGameMode(), player.getUserName());
                userNameOk = true;
            } catch (UserNameAlreadyTakenException e) {
                inputOutputManager.print("Username già in uso!");
            } catch (BrokenConnectionException e) {
                inputOutputManager.print("Error in creating connection");
                e.printStackTrace();
            } catch (TooManyUsersException e) {
                inputOutputManager.print("Partita piena, numero massimo di giocatori raggiunto\nArrivederci.");
            }
        }
    }

    @Override
    public void showRoom(List<String> players) {
        this.loginState.showRoom(players);
    }

    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) {
        int idChosen = this.initializationState.showMapsToChoose(listWp);
        try {
            this.server.windowPatternCardRequest(idChosen);
        } catch (BrokenConnectionException e) {
            inputOutputManager.print("");
            e.printStackTrace();
        }
    }

    @Override
    public void showCommonBoard(DiceDraftPool draft, SimplifiedWindowPatternCard wp){
        this.player.setWp(new WindowPatternCardView(wp));
        DieDraftPoolView draftPool = new DieDraftPoolView(draft);

        this.commonBoard.setDraftPool(draftPool);
        this.commonBoard.getPlayers().add(player);
        initializationState.showCommonBoard(draftPool, player.getWp());
    }

    @Override
    public void showCommand() {
        int command = initializationState.showCommand();
        switch (command){
            case 1: try{
                server.defaultMoveRequest();
            } catch (BrokenConnectionException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void giveProperObjectToFill(SetUpInformationUnit setInfoUnit) {
        initializationState.makeMove(commonBoard.getDraftPool(), player.getWp(), setInfoUnit);

        try {
            server.performMove(setInfoUnit);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during normal placement",e);
        }
    }


    public IFromClientToServer getServer() {
        return server;
    }

    public void setServer(IFromClientToServer server) {
        this.server = server;
    }
}
