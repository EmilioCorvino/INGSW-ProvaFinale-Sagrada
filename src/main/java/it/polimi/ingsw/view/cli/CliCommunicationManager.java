package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.view.cli.generalmanagers.InputOutputManager;

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
        view.getScannerThread().stopExecution();
        inputOutputManager.closeScanner();
        inputOutputManager.print("\nCHIUSURA APPLICAZIONE PER ROTTURA CONNESSIONE");
        System.exit(0);
    }
}
