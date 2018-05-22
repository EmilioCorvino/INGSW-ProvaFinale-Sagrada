package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.stateManagers.EndGameCli;
import it.polimi.ingsw.view.cli.stateManagers.GamePlayCli;
import it.polimi.ingsw.view.cli.stateManagers.LoginCli;
import it.polimi.ingsw.view.cli.stateManagers.SetUpGameCli;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public class CliView extends AViewMaster implements Serializable {

    /**
     * The network interface for the connection
     */
    IFromClientToServer server;

    /**
     * The port number use to create the socket connection
     */
    private int portNumber;

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
            }catch (RemoteException e){
                System.out.println("Indirizzo IP non corretto.");
                ipOk = false;
            }
        }

        System.out.println("Connessione stabilita.\n Procedere con il login.");

        while(!userNameOk){
            try {
                System.out.println(server);
                System.out.println(loginState);
                this.server.login(loginState.getUsername(), loginState.getGameMode());
                userNameOk = true;
            } catch (UserNameAlreadyTakenException e) {
                userNameOk = false;
                System.out.println("Username già in uso!");
            } catch (RemoteException e) {
                System.err.println("Error in creating connection");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showRoom(List<String> players) {
        this.loginState.showRoom(players);
    }
}
