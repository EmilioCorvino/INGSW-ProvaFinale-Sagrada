package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.simplified_view.SimplifiedDraftpool;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.die.diecontainers.DiceDraftPool;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.die.CommonBoardView;
import it.polimi.ingsw.view.cli.stateManagers.EndGameCli;
import it.polimi.ingsw.view.cli.stateManagers.GamePlayCli;
import it.polimi.ingsw.view.cli.stateManagers.LoginCli;
import it.polimi.ingsw.view.cli.stateManagers.SetUpGameCli;

import java.util.List;

public class CliView extends AViewMaster{

    /**
     * The user name of the player connected with the client.
     */
    private String userName;

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
                this.userName = loginState.getUsername();
                this.server.login(loginState.getGameMode(), userName);
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

    public void showCommonBoard(DiceDraftPool draft, WindowPatternCard wp){

    }

    public IFromClientToServer getServer() {
        return server;
    }

    public void setServer(IFromClientToServer server) {
        this.server = server;
    }
}
