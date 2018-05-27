package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.stateManagers.EndGameCli;
import it.polimi.ingsw.view.cli.stateManagers.GamePlayCli;
import it.polimi.ingsw.view.cli.stateManagers.LoginCli;
import it.polimi.ingsw.view.cli.stateManagers.SetUpGameCli;

import java.util.List;

public class CliView extends AViewMaster{

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
    private SetUpGameCli initializzationState;

    /**
     * The manager of the game play.
     */
    private GamePlayCli gameplaySate;

    /**
     * The manager of the game play.
     */
    private EndGameCli endGameState;

    public CliView(){
        inputOutputManager = new InputOutputManager();
        loginState = new LoginCli();
        initializzationState = new SetUpGameCli();
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
                this.server.login(loginState.getGameMode(), loginState.getUsername());
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

    public void chooseWP(){

    }

    @Override
    public void showRoom(List<String> players) {
        this.loginState.showRoom(players);
    }
}
