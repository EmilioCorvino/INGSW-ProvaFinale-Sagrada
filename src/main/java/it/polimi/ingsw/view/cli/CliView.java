package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.stateManagers.GamePlayCli;
import it.polimi.ingsw.view.cli.stateManagers.LoginCli;
import it.polimi.ingsw.view.cli.stateManagers.SetUpGameCli;

public class CliView extends AViewMaster {

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
     * This method create the connection and logs the player
     */
    @Override
    public void createConnection() {
        String ipAddress = loginState.getIp();
        this.server = loginState.chooseNetworkInterface(ipAddress);
        System.out.println("Connessione stabilita.\n Procedere con il login.");
        this.server.login(loginState.getUsername(), ipAddress, loginState.getGameMode());
    }

}