package it.polimi.ingsw.view.cli.managers;

import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.cli.managers.general.InputOutputManager;

public class CliCommunicationManager {
    /**
     * A reference to the view.
     */
    protected CliView view;

    /**
     * A reference to the inputOutputManager of the view
     */
    protected InputOutputManager inputOutputManager;

    /**
     * The network interface for the connection
     */
    protected IFromClientToServer server;

    public CliCommunicationManager(CliView view){
        this.view = view;
        this.inputOutputManager = view.getInputOutputManager();
        this.server = view.getServer();
    }

    public void setServer(IFromClientToServer server) {
        this.server = server;
    }

    protected void disconnect(){
        inputOutputManager.print("\nCHIUSURA APPLICAZIONE PER ROTTURA CONNESSIONE");
        view.forceLogOut();
    }
}